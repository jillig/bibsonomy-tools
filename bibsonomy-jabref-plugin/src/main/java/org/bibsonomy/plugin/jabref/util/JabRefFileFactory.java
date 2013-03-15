/*
 * Created on 08.03.2013
 */
package org.bibsonomy.plugin.jabref.util;

import net.sf.jabref.JabRefPreferences;

import org.bibsonomy.rest.client.util.MultiDirectoryFileFactory;
import org.bibsonomy.util.ValidationUtils;

public class JabRefFileFactory extends MultiDirectoryFileFactory {

	private static final String DEFAULT_FILE_DIRECTORY = System.getProperty("user.dir");
	
	public JabRefFileFactory() {
		super(null,null,null);
	}
	
	@Override
	public String getPsDirectory() {
		return getWithFallBack("psDirectory");
	}
	
	@Override
	public String getPdfDirectory() { 
		return getWithFallBack("pdfDirectory");
	}

	public String getWithFallBack(String name) {
		String rVal = JabRefPreferences.getInstance().get(name, "");
		if (ValidationUtils.present(rVal)) {
			return rVal;
		}
		return getFileDirectory();
	}

	@Override
	public String getFileDirectory() {
		return JabRefPreferences.getInstance().get("fileDirectory", DEFAULT_FILE_DIRECTORY);
	}
}
