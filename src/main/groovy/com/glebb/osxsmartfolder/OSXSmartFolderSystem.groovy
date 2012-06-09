package com.glebb.osxsmartfolder

import groovy.io.FileType


import java.util.List;

import net.pms.PMS
import net.pms.dlna.DLNAResource;
import net.pms.dlna.virtual.VirtualFolder;

import org.apache.commons.io.FilenameUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.jna.Platform

/*
 * Creates the VirtualFolder structure based on Smart Folders that are found
 * from the user's system.
 */
class OSXSmartFolderSystem {
	
	private static final SAVED_SEARCHES_FOLDER = System.getenv()['HOME'] + "/Library/Saved Searches/"
	
	static final Logger logger = LoggerFactory.getLogger(PMS.class)
	
	/*
	 * Creates the root folder and on OS X platform tries to populate it
	 * with Smart Folders child objects.
	 */
	public DLNAResource createFolderStructure() {
		VirtualFolder vf = new VirtualFolder("OSX Smart Folders", null)
		if (PlatformProxy.isMac()) {
			createSubFolders(vf);
		}
		else {
			logger.error("Cannot load OSX Smart Folders (not running on mac platform)")
		}
		return vf

	}
	
	private void createSubFolders(VirtualFolder rootVf) {
		List smart_folders = Utils.getListOfSmartFoldersFromFilesystem(SAVED_SEARCHES_FOLDER)
		if (!smart_folders.isEmpty()) {
			smart_folders.each {
				def basename = FilenameUtils.getBaseName(it.name)
				VirtualSmartFolder subVf = new VirtualSmartFolder(basename, null)
				rootVf.addChild(subVf)
			}
		}
		else {
			logger.info("No OSX Smart Folders found.")
		}
	}
	
}

/*
 * Collection of static helper methods which deal with filesystem and external
 * programs.
 */
class Utils {
	private static final Logger logger = LoggerFactory.getLogger(PMS.class)
	
	/*
	 * Execute any external command on system.
	 * @param query List of string, first being the command to be executed,
	 * followed by parameters as items in list. Return output lines as list, or
	 * empty list if command is not found. 
	 */
	static List execute(List query) {
		def l = []
		if (query.isEmpty()) return l
		try {
			def process = query.execute()
			process.in.eachLine { line ->
				l.add(line)
			}
		} catch (IOException e) {
			logger.error("Cannot execute " + query.toString().replace(",", ""))
			logger.error(e.message)
		}
		return l
	}
	
	/*
	 * Returns a list of .savedSearch File objects found from given path.
	 */
	static List getListOfSmartFoldersFromFilesystem(path) {
		def l = []
		def dir = new File(path)
		dir.eachFileRecurse (FileType.FILES) { file ->
			if ((!file.isHidden() && !file.isDirectory()) && file.getName().endsWith(".savedSearch"))
				l << file
		}
		return l
	}

}