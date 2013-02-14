/**
 *  
 *  JabRef Bibsonomy Plug-in - Plugin for the reference management 
 * 		software JabRef (http://jabref.sourceforge.net/) 
 * 		to fetch, store and delete entries from BibSonomy.
 *   
 *  Copyright (C) 2008 - 2011 Knowledge & Data Engineering Group, 
 *                            University of Kassel, Germany
 *                            http://www.kde.cs.uni-kassel.de/
 *  
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.bibsonomy.plugin.jabref.worker;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JEditorPane;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefFrame;
import net.sf.jabref.MetaData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bibsonomy.common.enums.GroupingEntity;
import org.bibsonomy.common.enums.ResourceType;
import org.bibsonomy.model.Tag;
import org.bibsonomy.plugin.jabref.PluginProperties;
import org.bibsonomy.plugin.jabref.action.ShowSettingsDialogAction;
import org.bibsonomy.rest.client.Bibsonomy;
import org.bibsonomy.rest.client.queries.get.GetTagsQuery;
import org.bibsonomy.rest.exceptions.AuthenticationException;
import org.bibsonomy.rest.exceptions.BadRequestOrResponseException;

/**
 * Fetch tags from the service and display them in a tag cloud
 * @author Waldemar Biller <biller@cs.uni-kassel.de>
 *
 */
public class RefreshTagListWorker extends AbstractPluginWorker {

	private static final Log LOG = LogFactory.getLog(RefreshTagListWorker.class);

	private JEditorPane tagCloud;
	
	private GroupingEntity grouping;
	private JabRefFrame jabRefFrame;
	private String groupingValue;
	private List<Tag> tags;

	public RefreshTagListWorker(JabRefFrame jabRefFrame, JEditorPane tagCloud, GroupingEntity grouping, String groupingValue) {
		
		this.tagCloud = tagCloud;
		this.grouping = grouping;
		this.groupingValue = groupingValue;
		this.jabRefFrame = jabRefFrame;
		this.tags = new LinkedList<Tag>();
	}
	
	public void run() {
		
		Bibsonomy client = new Bibsonomy(PluginProperties.getUsername(), PluginProperties.getApiKey());
		client.setApiURL(PluginProperties.getApiUrl());
		
		MetaData metaData = null;
		Vector<String> keywords = null;
		if(jabRefFrame.basePanel() != null) {
			 metaData = jabRefFrame.basePanel().metaData();
			keywords = metaData.getData(Globals.SELECTOR_META_PREFIX + "keywords");
		}
		
		int start = 0, end = PluginProperties.getTagCloudSize(), max = 1, min = 1;
		
		//in case of fetching all tags we only get the first 100 most popular
		if(grouping == GroupingEntity.ALL && PluginProperties.getTagCloudSize() > 100)
			end = 100;
		
		GetTagsQuery getTagsQuery = new GetTagsQuery(start, end);
		getTagsQuery.setResourceType(ResourceType.BIBTEX);
		getTagsQuery.setGrouping(grouping, groupingValue);
		getTagsQuery.setOrder(PluginProperties.getTagCloudOrder());
		
		List<Tag> result = null;
		try {

			client.executeQuery(getTagsQuery);
			result = getTagsQuery.getResult();
			tags.addAll(result);
			
		} catch(AuthenticationException ex) {
			(new ShowSettingsDialogAction(jabRefFrame)).actionPerformed(null);
		} catch(BadRequestOrResponseException ex) {
			LOG.error("Reached maximum number of tags", ex);
		} catch(Exception ex) {
			LOG.error("Error updating tags", ex);
		}
		
		for(Tag tag : tags) {
			
			switch(grouping) {
				
				case USER:
					max = Math.max(max, tag.getUsercount());
					min = Math.min(min, tag.getUsercount());
					break;
				default:
					max = Math.max(max, tag.getGlobalcount());
					min = Math.min(min, tag.getGlobalcount());
			}
		}
		
		StringBuffer tagList = new StringBuffer();
		tagList.append("<div style='text-align: center; font-family: Arial, Helvetica, sans;'>");
		int size = 0;
		
		tagCloud.removeAll();
		
		// calculate the tag cloud
		for(Tag tag : tags) {
			
			if(keywords != null)
				keywords.add(tag.getName());
			jabRefFrame.output("Added tag: " + tag.getName());

			switch(grouping) {
				
				case USER:
					size = Math.round(12 * (tag.getUsercount() - min) / (max - min)) + 12;
					break;
				default:
					size = Math.round(12 * (tag.getGlobalcount() - min) / (max - min)) + 12;
			}
			
			tagList.append("<span style='display: inline'>"
								+ "<a style='color: #006699; display: inline; text-decoration: none; font-size: "
									+ size
									+ "' href='"
									+ tag.getName()
									+ "'>"
									+ tag.getName()
								+ "</a>"
							+ "</span> ");
			
			
		}
		
		tagList.append("</div>");
		tagCloud.setText(tagList.toString());
		
		jabRefFrame.validate();
		jabRefFrame.repaint();
		
		if(metaData != null)
			metaData.putData(Globals.SELECTOR_META_PREFIX + "keywords", keywords);
		
		jabRefFrame.output("Done.");
	}

}
