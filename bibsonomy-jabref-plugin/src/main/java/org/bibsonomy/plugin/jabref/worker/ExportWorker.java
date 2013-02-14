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

import java.util.List;

import net.sf.jabref.BibtexEntry;
import net.sf.jabref.JabRefFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bibsonomy.common.enums.GroupingEntity;
import org.bibsonomy.model.Post;
import org.bibsonomy.model.Resource;
import org.bibsonomy.model.User;
import org.bibsonomy.plugin.jabref.PluginProperties;
import org.bibsonomy.plugin.jabref.action.ShowSettingsDialogAction;
import org.bibsonomy.plugin.jabref.util.JabRefModelConverter;
import org.bibsonomy.plugin.jabref.util.WorkerUtil;
import org.bibsonomy.rest.client.Bibsonomy;
import org.bibsonomy.rest.client.queries.post.CreatePostQuery;

import org.bibsonomy.rest.client.queries.put.ChangePostQuery;
import org.bibsonomy.rest.exceptions.AuthenticationException;

/**
 * Export an entry to service
 * @author Waldemar Biller <biller@cs.uni-kassel.de>
 *
 */
public class ExportWorker extends AbstractPluginWorker {

	private static final Log LOG = LogFactory.getLog(ExportWorker.class);
	
	private JabRefFrame jabRefFrame;
	private List<BibtexEntry> entries;

	public void run() {
		Bibsonomy client = new Bibsonomy(PluginProperties.getUsername(), PluginProperties.getApiKey());
		client.setApiURL(PluginProperties.getApiUrl());
		
		try {
			
			for(BibtexEntry entry : entries) {
				
				String intrahash = entry.getField("intrahash");
				jabRefFrame.output("Exporting post " + entry.getCiteKey());
				
				if(entry.getField("groups") != null) {
					
					// check grouping. replace public with private if user set default visibility to private and vice versa
					if(entry.getField("groups").contains("private") && PluginProperties.getDefaultVisibilty() == GroupingEntity.ALL)
						entry.setField("groups", entry.getField("groups").replaceAll("private", "public"));
						
					if(entry.getField("groups").contains("public") && PluginProperties.getDefaultVisibilty() == GroupingEntity.USER)
						entry.setField("groups", entry.getField("groups").replaceAll("public", "private"));
				}
				
				// add private or public if groups is empty
				if(entry.getField("groups") == null || "".equals(entry.getField("groups"))) {
					switch(PluginProperties.getDefaultVisibilty()) {
						case USER:
							entry.setField("groups", "private");
							break;
						default:
							entry.setField("groups", "public");
					}
				}
				
				entry.setField("username", PluginProperties.getUsername());
				String owner = entry.getField("owner");
				entry.clearField("owner");
				
				if(intrahash != null && !"".equals(intrahash))
					changePost(client, entry);
				else createPost(client, entry);
				
				entry.setField("owner", owner);
				
				String files = entry.getField("file");
				if(files != null && !"".equals(files))
					WorkerUtil.performAsynchronously(new UploadDocumentsWorker(jabRefFrame, entry.getField("intrahash"), files));
			}
			
		} catch(AuthenticationException ex) {
			
			(new ShowSettingsDialogAction(jabRefFrame)).actionPerformed(null);
		} catch(Exception ex) {
			
			LOG.error("Failed to export post ", ex);
		} catch (Throwable ex) {
			
			LOG.error("Failed to export post ", ex);
		}
		jabRefFrame.output("Done.");
	}
	
	private void changePost(Bibsonomy client, BibtexEntry entry) throws Exception {
		
		Post<? extends Resource> post = JabRefModelConverter.convertEntry(entry);
		
		if(post.getUser() == null)
			post.setUser(new User(PluginProperties.getUsername()));
		
		ChangePostQuery changePostQuery = new ChangePostQuery(PluginProperties.getUsername(), post.getResource().getIntraHash(), post);
		
		client.executeQuery(changePostQuery);
	}
	
	private void createPost(Bibsonomy client, BibtexEntry entry) throws Exception {
		
		Post<? extends Resource> post = JabRefModelConverter.convertEntry(entry);
		
		if(post.getUser() == null)
			post.setUser(new User(PluginProperties.getUsername()));
		
		CreatePostQuery createPostQuery = new CreatePostQuery(PluginProperties.getUsername(), post);
		client.executeQuery(createPostQuery);
		
		entry.setField("intrahash", createPostQuery.getResult());
	}

	public ExportWorker(JabRefFrame jabRefFrame, List<BibtexEntry> entries) {
		
		this.jabRefFrame = jabRefFrame;
		this.entries = entries;
	}
}
