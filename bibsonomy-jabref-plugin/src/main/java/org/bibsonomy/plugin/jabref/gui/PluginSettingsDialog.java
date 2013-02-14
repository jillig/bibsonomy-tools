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

package org.bibsonomy.plugin.jabref.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.bibsonomy.common.enums.GroupingEntity;
import org.bibsonomy.model.enums.Order;
import org.bibsonomy.plugin.jabref.PluginGlobals;
import org.bibsonomy.plugin.jabref.PluginProperties;
import org.bibsonomy.plugin.jabref.action.ClosePluginSettingsDialogByCancelAction;
import org.bibsonomy.plugin.jabref.action.ClosePluginSettingsDialogBySaveAction;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class PluginSettingsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel buttonsPanel = null;
	private JButton saveButton = null;
	private JPanel whitespacePanel = null;
	private JButton cancelButton = null;
	private JTabbedPane settingsPane = null;
	private JPanel generalSettingsPanel = null;
	private JPanel credentialsPanel = null;
	private JLabel userNameLabel = null;
	private JTextField usernameTextField = null;
	private JLabel apiKeyLabel = null;
	private JTextField apiKeyTextField = null;
	private JCheckBox storeAPIKeyCheckBox = null;
	private JLabel apiKeyHintLabel = null;
	private JCheckBox ignoreOneTagWarningCheckBox = null;
	private JCheckBox updateTagsCheckBox = null;
	private JCheckBox uploadDocumentsCheckBox = null;
	private JCheckBox downloadDocumentsCheckBox = null;
	private JLabel defaultVisibilityLabel = null;
	private JComboBox defaultVisibilityComboBox = null;
	private JLabel extraFieldsLabel = null;
	private JTextField extraFieldsTextField = null;
	private JLabel extraFieldsHintLabel = null;
	private JPanel generalSettingsWhitespacePanel = null;
	private JLabel numberOfPostsLabel = null;
	private JSpinner numberOfPostsSpinner = null;
	private JCheckBox noWarningOnMorePostsCheckBox = null;
	private JLabel tagSizeLabel = null;
	private JSpinner tagCloudSizeSpinner = null;
	private JLabel tagCloudOrderLabel = null;
	private JComboBox tagCloudOrderComboBox = null;
	private JLabel tagCloudOrderHintLabel = null;
	private JLabel changingCredentialsHintLabel = null;
	/**
	 * @param owner
	 */
	public PluginSettingsDialog(Frame owner) {
		super(owner);
		if(PluginProperties.getUsername().equals(PluginGlobals.API_USERNAME))
			JOptionPane.showMessageDialog(this, "<html>PLEASE NOTE: the current API access data is for testing purposes only.\n"
									+ "You can up- and download entries, and after logging in you can see and\n"
									+ "edit your entries on www.bibsonomy.org. Do not use this account for\n"
									+ "personal data, as it is accessible by everyone.\n\n"
									+ "To obtain your own personal API key,\n"
									+ "visit http://www.bibsonomy.org/help/doc/gettingaccess.html.", "Demo mode", JOptionPane.INFORMATION_MESSAGE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(677, 513);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(700, 460));
		this.setModal(true);
		this.setMaximumSize(new Dimension(700, 460));
		this.setMinimumSize(new Dimension(700, 460));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(3);
			borderLayout.setVgap(3);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			jContentPane.add(getButtonsPanel(), BorderLayout.SOUTH);
			jContentPane.add(getSettingsPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes buttonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(0, 0, 0, 3);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridy = 0;
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new GridBagLayout());
			buttonsPanel.add(getWhitespacePanel(), gridBagConstraints);
			buttonsPanel.add(getSaveButton(), gridBagConstraints1);
			buttonsPanel.add(getCancelButton(), gridBagConstraints11);
		}
		return buttonsPanel;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton(new ClosePluginSettingsDialogBySaveAction(this, getUsernameTextField(), getApiKeyTextField(), getStoreAPIKeyCheckBox(), getNumberOfPostsSpinner(), getTagCloudSizeSpinner(), getIgnoreOneTagWarningCheckBox(), getUpdateTagsCheckBox(), getUploadDocumentsCheckBox(), getDownloadDocumentsCheckBox(), getDefaultVisibilityComboBox(), getNoWarningOnMorePostsCheckBox(), getExtraFieldsTextField(), getTagCloudOrderComboBox()));
		}
		return saveButton;
	}

	/**
	 * This method initializes whitespacePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getWhitespacePanel() {
		if (whitespacePanel == null) {
			whitespacePanel = new JPanel();
			whitespacePanel.setLayout(new GridBagLayout());
		}
		return whitespacePanel;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton(new ClosePluginSettingsDialogByCancelAction(this));
		}
		return cancelButton;
	}

	/**
	 * This method initializes settingsPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getSettingsPane() {
		if (settingsPane == null) {
			settingsPane = new JTabbedPane();
			settingsPane.addTab("General", new ImageIcon(getClass().getResource("/images/wrench-screwdriver.png")), getGeneralSettingsPanel(), null);
		}
		return settingsPane;
	}

	/**
	 * This method initializes generalSettingsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralSettingsPanel() {
		if (generalSettingsPanel == null) {
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridx = 2;
			gridBagConstraints27.anchor = GridBagConstraints.WEST;
			gridBagConstraints27.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints27.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints27.gridy = 3;
			tagCloudOrderHintLabel = new JLabel();
			tagCloudOrderHintLabel.setText("Tag cloud ordering is not available when importing posts from all users");
			tagCloudOrderHintLabel.setFont(new Font("Dialog", Font.ITALIC, 10));
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.fill = GridBagConstraints.BOTH;
			gridBagConstraints26.gridy = 2;
			gridBagConstraints26.weightx = 1.0;
			gridBagConstraints26.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints26.gridwidth = 2;
			gridBagConstraints26.gridx = 2;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.insets = new Insets(0, 0, 3, 3);
			gridBagConstraints16.gridy = 2;
			tagCloudOrderLabel = new JLabel();
			tagCloudOrderLabel.setText("Tag cloud order");
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.BOTH;
			gridBagConstraints25.gridy = 1;
			gridBagConstraints25.weightx = 1.0;
			gridBagConstraints25.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints25.gridwidth = 2;
			gridBagConstraints25.gridx = 2;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 1;
			gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints24.insets = new Insets(0, 0, 3, 3);
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridy = 1;
			tagSizeLabel = new JLabel();
			tagSizeLabel.setText("Tag cloud size");
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.gridwidth = 4;
			gridBagConstraints23.gridy = 5;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.BOTH;
			gridBagConstraints22.gridy = 4;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.gridwidth = 1;
			gridBagConstraints22.ipadx = 2;
			gridBagConstraints22.ipady = 2;
			gridBagConstraints22.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints22.gridx = 2;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.insets = new Insets(0, 0, 0, 3);
			gridBagConstraints21.gridy = 4;
			numberOfPostsLabel = new JLabel();
			numberOfPostsLabel.setText("Number of Posts to fetch per Request");
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 1;
			gridBagConstraints20.fill = GridBagConstraints.BOTH;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.weighty = 1.0;
			gridBagConstraints20.gridwidth = 8;
			gridBagConstraints20.gridy = 19;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 2;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.gridwidth = 4;
			gridBagConstraints19.gridy = 17;
			extraFieldsHintLabel = new JLabel();
			extraFieldsHintLabel.setText("You have to restart JabRef in order to see newly added or removed extra fields.");
			extraFieldsHintLabel.setFont(new Font("Dialog", Font.ITALIC, 10));
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.BOTH;
			gridBagConstraints18.gridy = 16;
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.ipadx = 2;
			gridBagConstraints18.ipady = 2;
			gridBagConstraints18.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints18.gridwidth = 5;
			gridBagConstraints18.gridx = 2;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 1;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.weightx = 0.1;
			gridBagConstraints17.insets = new Insets(0, 0, 0, 3);
			gridBagConstraints17.gridy = 16;
			extraFieldsLabel = new JLabel();
			extraFieldsLabel.setText("Extra fields");
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridy = 14;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.insets = new Insets(0, 0, 3, 3);
			gridBagConstraints15.gridwidth = 1;
			gridBagConstraints15.gridx = 2;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.weightx = 0.1;
			gridBagConstraints14.insets = new Insets(0, 0, 0, 3);
			gridBagConstraints14.gridy = 14;
			defaultVisibilityLabel = new JLabel();
			defaultVisibilityLabel.setText("Default visibility");
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints13.gridwidth = 5;
			gridBagConstraints13.gridy = 10;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints12.gridwidth = 5;
			gridBagConstraints12.gridy = 9;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 2;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints10.gridwidth = 5;
			gridBagConstraints10.gridy = 8;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints9.gridwidth = 7;
			gridBagConstraints9.gridy = 6;
			apiKeyHintLabel = new JLabel();
			apiKeyHintLabel.setText("You can find your API key at the settings page at http://www.bibsonomy.org");
			apiKeyHintLabel.setFont(new Font("Dialog", Font.ITALIC, 10));
			apiKeyLabel = new JLabel();
			apiKeyLabel.setText("API key");
			userNameLabel = new JLabel();
			userNameLabel.setText("Username");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints2.gridwidth = 6;
			gridBagConstraints2.gridy = 0;
			generalSettingsPanel = new JPanel();
			generalSettingsPanel.setLayout(new GridBagLayout());
			generalSettingsPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			generalSettingsPanel.add(getCredentialsPanel(), gridBagConstraints2);
			generalSettingsPanel.add(getIgnoreOneTagWarningCheckBox(), gridBagConstraints9);
			generalSettingsPanel.add(getUpdateTagsCheckBox(), gridBagConstraints10);
			generalSettingsPanel.add(getUploadDocumentsCheckBox(), gridBagConstraints12);
			generalSettingsPanel.add(getDownloadDocumentsCheckBox(), gridBagConstraints13);
			generalSettingsPanel.add(defaultVisibilityLabel, gridBagConstraints14);
			generalSettingsPanel.add(getDefaultVisibilityComboBox(), gridBagConstraints15);
			generalSettingsPanel.add(extraFieldsLabel, gridBagConstraints17);
			generalSettingsPanel.add(getExtraFieldsTextField(), gridBagConstraints18);
			generalSettingsPanel.add(extraFieldsHintLabel, gridBagConstraints19);
			generalSettingsPanel.add(getGeneralSettingsWhitespacePanel(), gridBagConstraints20);
			generalSettingsPanel.add(numberOfPostsLabel, gridBagConstraints21);
			generalSettingsPanel.add(getNumberOfPostsSpinner(), gridBagConstraints22);
			generalSettingsPanel.add(getNoWarningOnMorePostsCheckBox(), gridBagConstraints23);
			generalSettingsPanel.add(tagSizeLabel, gridBagConstraints24);
			generalSettingsPanel.add(getTagCloudSizeSpinner(), gridBagConstraints25);
			generalSettingsPanel.add(tagCloudOrderLabel, gridBagConstraints16);
			generalSettingsPanel.add(getTagCloudOrderComboBox(), gridBagConstraints26);
			generalSettingsPanel.add(tagCloudOrderHintLabel, gridBagConstraints27);
		}
		return generalSettingsPanel;
	}

	/**
	 * This method initializes credentialsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCredentialsPanel() {
		if (credentialsPanel == null) {
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridx = 1;
			gridBagConstraints28.anchor = GridBagConstraints.WEST;
			gridBagConstraints28.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints28.gridy = 4;
			changingCredentialsHintLabel = new JLabel();
			changingCredentialsHintLabel.setText("Don't forget to hit the refresh button after changing credentials!");
			changingCredentialsHintLabel.setFont(new Font("Dialog", Font.ITALIC, 10));
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 3;
			gridBagConstraints8.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(0, 0, 3, 0);
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.ipadx = 2;
			gridBagConstraints6.ipady = 2;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.insets = new Insets(0, 0, 3, 0);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(0, 0, 3, 3);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.weightx = 0.1;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.ipadx = 2;
			gridBagConstraints4.ipady = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.insets = new Insets(0, 0, 3, 0);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 0, 3, 3);
			gridBagConstraints3.gridx = -1;
			gridBagConstraints3.gridy = -1;
			gridBagConstraints3.weightx = 0.1;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			credentialsPanel = new JPanel();
			credentialsPanel.setLayout(new GridBagLayout());
			credentialsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(null, "Credentials", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)), BorderFactory.createEmptyBorder(3, 3, 3, 3)));
			credentialsPanel.add(userNameLabel, gridBagConstraints3);
			credentialsPanel.add(getUsernameTextField(), gridBagConstraints4);
			credentialsPanel.add(apiKeyLabel, gridBagConstraints5);
			credentialsPanel.add(getApiKeyTextField(), gridBagConstraints6);
			credentialsPanel.add(getStoreAPIKeyCheckBox(), gridBagConstraints7);
			credentialsPanel.add(apiKeyHintLabel, gridBagConstraints8);
			credentialsPanel.add(changingCredentialsHintLabel, gridBagConstraints28);
		}
		return credentialsPanel;
	}

	/**
	 * This method initializes usernameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getUsernameTextField() {
		if (usernameTextField == null) {
			usernameTextField = new JTextField(PluginProperties.getUsername());
		}
		return usernameTextField;
	}

	/**
	 * This method initializes apiKeyTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getApiKeyTextField() {
		if (apiKeyTextField == null) {
			apiKeyTextField = new JTextField(PluginProperties.getApiKey());
		}
		return apiKeyTextField;
	}

	/**
	 * This method initializes storeAPIKeyCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getStoreAPIKeyCheckBox() {
		if (storeAPIKeyCheckBox == null) {
			storeAPIKeyCheckBox = new JCheckBox();
			storeAPIKeyCheckBox.setText("Store API key");
			storeAPIKeyCheckBox.setSelected(PluginProperties.getStoreApiKey());
		}
		return storeAPIKeyCheckBox;
	}

	/**
	 * This method initializes ignoreOneTagWarningCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getIgnoreOneTagWarningCheckBox() {
		if (ignoreOneTagWarningCheckBox == null) {
			ignoreOneTagWarningCheckBox = new JCheckBox();
			ignoreOneTagWarningCheckBox.setText("Do not warn me, if a post has no tags assigned");
			ignoreOneTagWarningCheckBox.setSelected(PluginProperties.ignoreNoTagsAssigned());
		}
		return ignoreOneTagWarningCheckBox;
	}

	/**
	 * This method initializes updateTagsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getUpdateTagsCheckBox() {
		if (updateTagsCheckBox == null) {
			updateTagsCheckBox = new JCheckBox();
			updateTagsCheckBox.setText("Update tags on startup");
			updateTagsCheckBox.setSelected(PluginProperties.getUpdateTagsOnStartUp());
		}
		return updateTagsCheckBox;
	}

	/**
	 * This method initializes uploadDocumentsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getUploadDocumentsCheckBox() {
		if (uploadDocumentsCheckBox == null) {
			uploadDocumentsCheckBox = new JCheckBox();
			uploadDocumentsCheckBox.setText("Upload documents on export");
			uploadDocumentsCheckBox.setSelected(PluginProperties.getUploadDocumentsOnExport());
		}
		return uploadDocumentsCheckBox;
	}

	/**
	 * This method initializes downloadDocumentsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getDownloadDocumentsCheckBox() {
		if (downloadDocumentsCheckBox == null) {
			downloadDocumentsCheckBox = new JCheckBox();
			downloadDocumentsCheckBox.setText("Download documents on import");
			downloadDocumentsCheckBox.setSelected(PluginProperties.getDownloadDocumentsOnImport());
		}
		return downloadDocumentsCheckBox;
	}

	/**
	 * This method initializes defaultVisibilityComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getDefaultVisibilityComboBox() {
		if (defaultVisibilityComboBox == null) {
			List<GroupingComboBoxItem> items = new LinkedList<GroupingComboBoxItem>();
			
			items.add(new GroupingComboBoxItem(GroupingEntity.ALL, "Public"));
			items.add(new GroupingComboBoxItem(GroupingEntity.USER, "Private"));
			
			defaultVisibilityComboBox = new JComboBox();
			
			for(GroupingComboBoxItem item : items)
				defaultVisibilityComboBox.addItem(item);
			
			int itemCount = defaultVisibilityComboBox.getItemCount();
			for(int i = 0; i < itemCount; i++) {
				if(((GroupingComboBoxItem)defaultVisibilityComboBox.getItemAt(i)).getKey() == PluginProperties.getDefaultVisibilty())
					defaultVisibilityComboBox.setSelectedItem(defaultVisibilityComboBox.getItemAt(i));
			}
		}
		return defaultVisibilityComboBox;
	}

	/**
	 * This method initializes extraFieldsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getExtraFieldsTextField() {
		if (extraFieldsTextField == null) {
			extraFieldsTextField = new JTextField();
			extraFieldsTextField.setText(PluginProperties.getExtraTabFields());
		}
		return extraFieldsTextField;
	}

	/**
	 * This method initializes generalSettingsWhitespacePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGeneralSettingsWhitespacePanel() {
		if (generalSettingsWhitespacePanel == null) {
			generalSettingsWhitespacePanel = new JPanel();
			generalSettingsWhitespacePanel.setLayout(new GridBagLayout());
		}
		return generalSettingsWhitespacePanel;
	}

	/**
	 * This method initializes numberOfPostsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JSpinner getNumberOfPostsSpinner() {
		if (numberOfPostsSpinner == null) {
			numberOfPostsSpinner = new JSpinner(new SpinnerNumberModel(PluginProperties.getNumberOfPostsPerRequest(), 1, 500, 1));
		}
		return numberOfPostsSpinner;
	}

	/**
	 * This method initializes noWarningOnMorePostsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getNoWarningOnMorePostsCheckBox() {
		if (noWarningOnMorePostsCheckBox == null) {
			noWarningOnMorePostsCheckBox = new JCheckBox();
			noWarningOnMorePostsCheckBox.setText("Do not warn me, if more posts are available");
			noWarningOnMorePostsCheckBox.setSelected(PluginProperties.getIgnoreMorePostsWarning());
		}
		return noWarningOnMorePostsCheckBox;
	}
	
	private JSpinner getTagCloudSizeSpinner() {
		
		if(tagCloudSizeSpinner == null) {
			
			tagCloudSizeSpinner = new JSpinner(new SpinnerNumberModel(PluginProperties.getTagCloudSize(), 20, 1000, 1));
		}
		
		return tagCloudSizeSpinner;
	}

	/**
	 * This method initializes tagCloudOrderComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTagCloudOrderComboBox() {
		if (tagCloudOrderComboBox == null) {
			
			OrderComboBoxItem[] items = new OrderComboBoxItem[] {
					
				new OrderComboBoxItem(Order.FREQUENCY, "Frequency"),
				new OrderComboBoxItem(Order.ALPH, "Alphabethical"),
				new OrderComboBoxItem(Order.FOLKRANK, "Folkrank"),
				new OrderComboBoxItem(Order.ADDED, "Date")
			};
			
			tagCloudOrderComboBox = new JComboBox(items);
			
			int itemCount = tagCloudOrderComboBox.getItemCount();
			for(int i = 0; i < itemCount; i++) {
				if(((OrderComboBoxItem)tagCloudOrderComboBox.getItemAt(i)).getKey() == PluginProperties.getTagCloudOrder())
					tagCloudOrderComboBox.setSelectedItem(tagCloudOrderComboBox.getItemAt(i));
			}
		}
		return tagCloudOrderComboBox;
	}

}  //  @jve:decl-index=0:visual-constraint="21,17"
