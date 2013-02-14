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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bibsonomy.plugin.jabref.PluginProperties;
import org.bibsonomy.plugin.jabref.action.ShowSettingsDialogAction;
import org.bibsonomy.rest.client.Bibsonomy;
import org.bibsonomy.rest.client.queries.delete.DeletePostQuery;
import org.bibsonomy.rest.exceptions.AuthenticationException;

import net.sf.jabref.BibtexEntry;
import net.sf.jabref.JabRefFrame;

/**
 * Delete a Post from the service
 * @author Waldemar Biller <biller@cs.uni-kassel.de>
 *
 */
public class DeletePostsWorker extends AbstractPluginWorker {

	private static final Log LOG = LogFactory.getLog(DeletePostsWorker.class);
	
	private JabRefFrame jabRefFrame;
	private BibtexEntry[] entries;

	public void run() {
		
		Bibsonomy client = new Bibsonomy(PluginProperties.getUsername(), PluginProperties.getApiKey());
		client.setApiURL(PluginProperties.getApiUrl());
		
		for(BibtexEntry entry : entries) {
			
			String intrahash = entry.getField("intrahash");
			String username = entry.getField("username");
			if(intrahash == null || "".equals(intrahash) || (intrahash != null && !PluginProperties.getUsername().equals(username)))
				continue;
			
			DeletePostQuery deletePostQuery = new DeletePostQuery(PluginProperties.getUsername(), intrahash);
			try {
				
				client.executeQuery(deletePostQuery);
				jabRefFrame.output("Deleting post " + intrahash);
				entry.clearField("intrahash");
				entry.clearField("interhash");
			} catch(AuthenticationException ex) {
				
				(new ShowSettingsDialogAction(jabRefFrame)).actionPerformed(null);
			} catch(Exception ex) {
				
				LOG.error("Failed deleting post " + intrahash);
			}
		}
		jabRefFrame.output("Done.");
	}

	public DeletePostsWorker(JabRefFrame jabRefFrame, BibtexEntry[] entries) {
		
		this.jabRefFrame = jabRefFrame;
		this.entries = entries;
	}
}
