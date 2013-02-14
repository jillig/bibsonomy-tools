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

import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.jabref.BasePanel;

/**
 * {@link TabbedPaneChangeListener} add a ChangeListener to the Database.
 * @author Waldemar Biller <biller@cs.uni-kassel.de>
 *
 */
public class TabbedPaneChangeListener implements ChangeListener {

	private PluginDataBaseChangeListener databaseChangeListener;

	public void stateChanged(ChangeEvent e) {
		
		if(e.getSource() instanceof JTabbedPane) {
			
			JTabbedPane pane = (JTabbedPane) e.getSource();
			for(Component c : pane.getComponents()) {
				
				BasePanel bp = (BasePanel) c;
				if(bp.database() != null) {
					
					bp.database().addDatabaseChangeListener(databaseChangeListener);
				}
			}
			
		}
	}
	
	public TabbedPaneChangeListener(PluginDataBaseChangeListener l) {
		
		this.databaseChangeListener = l;
	}

}
