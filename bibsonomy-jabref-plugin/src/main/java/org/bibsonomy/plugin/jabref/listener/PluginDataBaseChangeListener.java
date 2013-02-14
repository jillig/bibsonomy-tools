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

package org.bibsonomy.plugin.jabref.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bibsonomy.plugin.jabref.util.WorkerUtil;
import org.bibsonomy.plugin.jabref.worker.DownloadDocumentsWorker;

import net.sf.jabref.BibtexEntry;
import net.sf.jabref.DatabaseChangeEvent;
import net.sf.jabref.DatabaseChangeListener;
import net.sf.jabref.JabRefFrame;

/**
 * {@link PluginDataBaseChangeListener} runs the {@link DownloadDocumentsWorker} as soon as a new entry was added
 *  to the database
 * @author Waldemar Biller <biller@cs.uni-kassel.de>
 *
 */
public class PluginDataBaseChangeListener implements DatabaseChangeListener {

	private static final Log LOG = LogFactory.getLog(PluginDataBaseChangeListener.class);
	
	private JabRefFrame jabRefFrame;
	
	public PluginDataBaseChangeListener(JabRefFrame jabRefFrame) {
		
		this.jabRefFrame = jabRefFrame;
		
	}
	public void databaseChanged(DatabaseChangeEvent event) {
		
		if(event.getType() == DatabaseChangeEvent.ADDED_ENTRY) {
			
			BibtexEntry entry = event.getSource().getEntryById(event.getEntry().getId());
			DownloadDocumentsWorker worker = new DownloadDocumentsWorker(jabRefFrame, entry, true);
			try {
				WorkerUtil.performAsynchronously(worker);
			} catch (Throwable e) {
				LOG.error("Error while downloading private documents", e);
			}
		}
		
	}

}
