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

import java.net.URLEncoder;

import net.sf.jabref.BibtexEntry;
import net.sf.jabref.JabRefFrame;
import net.sf.jabref.JabRefPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bibsonomy.model.BibTex;
import org.bibsonomy.model.Document;
import org.bibsonomy.model.Post;
import org.bibsonomy.plugin.jabref.PluginProperties;
import org.bibsonomy.plugin.jabref.action.ShowSettingsDialogAction;
import org.bibsonomy.rest.client.Bibsonomy;

import org.bibsonomy.rest.client.queries.get.GetPostDetailsQuery;
import org.bibsonomy.rest.client.queries.get.GetPostDocumentQuery;
import org.bibsonomy.rest.exceptions.AuthenticationException;
import org.bibsonomy.util.file.FileUtil;

import ca.odell.glazedlists.BasicEventList;

/**
 * Download all private documents of a post from the service
 * @author Waldemar Biller <biller@cs.uni-kassel.de>
 *
 */
public class DownloadDocumentsWorker extends AbstractPluginWorker {

	private static final Log LOG = LogFactory.getLog(DownloadDocumentsWorker.class);
	
	private static final String BIBTEX_FILE_FIELD = "file";
	
	private static final String DEFAULT_FILE_DIRECTORY = System.getProperty("user.dir");
	private BibtexEntry entry;
	private JabRefFrame jabRefFrame;
	private boolean isImport;
	
	public DownloadDocumentsWorker(JabRefFrame jabRefFrame, BibtexEntry entry, boolean isImport) {
		
		this.entry = entry;
		this.jabRefFrame = jabRefFrame;
		this.isImport = isImport;
	}

	public void run() {
		
		if(isImport && !PluginProperties.getDownloadDocumentsOnImport())
			return;
		
		Bibsonomy client = new Bibsonomy(PluginProperties.getUsername(), PluginProperties.getApiKey());
		client.setApiURL(PluginProperties.getApiUrl());
		
		String intrahash = entry.getField("intrahash");
		if(intrahash != null && !"".equals(intrahash)) {
			
			GetPostDetailsQuery getPostDetailsQuery = new GetPostDetailsQuery(PluginProperties.getUsername(), intrahash);
			
			try {
				client.executeQuery(getPostDetailsQuery);
			} catch (AuthenticationException e) {
				(new ShowSettingsDialogAction(jabRefFrame)).actionPerformed(null);
			} catch (Exception e) {
				LOG.error("Failed getting details for post " + intrahash, e);
			}
			
			@SuppressWarnings("unchecked")
			Post<BibTex> post = (Post<BibTex>) getPostDetailsQuery.getResult();
			
			for(Document document : post.getResource().getDocuments()) {
				
				jabRefFrame.output("Downloading: " + document.getFileName());
				GetPostDocumentQuery getPostDocumentQuery = null;
				try {
					getPostDocumentQuery = new GetPostDocumentQuery(
							PluginProperties.getUsername(), intrahash, 
							URLEncoder.encode(document.getFileName(), "UTF-8"), 
							JabRefPreferences.getInstance().get("fileDirectory", DEFAULT_FILE_DIRECTORY), 
							JabRefPreferences.getInstance().get("pdfDirectory", DEFAULT_FILE_DIRECTORY), 
							JabRefPreferences.getInstance().get("psDirectory", DEFAULT_FILE_DIRECTORY));
					
					client.executeQuery(getPostDocumentQuery);
				} catch (Exception ex) {
					LOG.error("Failed downloading file: " + document.getFileName(), ex);
				}
				
				try {
					BasicEventList<BibtexEntry> list = new BasicEventList<BibtexEntry>();
					String downloadedFileBibTex = ":" + document.getFileName() + ":" + FileUtil.getFileExtension(document.getFileName()).toUpperCase();
					
					String entryFileValue = entry.getField(BIBTEX_FILE_FIELD);
					
					list.getReadWriteLock().writeLock().lock();
					list.add(entry);
					if(entryFileValue != null && !"".equals(entryFileValue)) {
						
						if(!entryFileValue.contains(downloadedFileBibTex))
							entry.setField(BIBTEX_FILE_FIELD, entryFileValue + ";" + downloadedFileBibTex);
					} else entry.setField(BIBTEX_FILE_FIELD, downloadedFileBibTex);
					list.getReadWriteLock().writeLock().lock();
					
				} catch (AuthenticationException e) {
					(new ShowSettingsDialogAction(jabRefFrame)).actionPerformed(null);
				} catch (Exception e) {
					LOG.error("Failed adding file to entry " + entry.getCiteKey(), e);
				}
				
			}
		}
		
		jabRefFrame.output("Done.");
	}
}
