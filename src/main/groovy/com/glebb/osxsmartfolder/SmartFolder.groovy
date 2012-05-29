package com.glebb.osxsmartfolder

import groovy.io.FileType

import java.util.List;

import net.pms.PMS

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class SmartFolderHelper {
	
	/*
	 * Returns a list of .savedSearch files found from the given path
	 */
	List getSmartFoldersFromFileSystem(path) {
		def l = []
		def dir = new File(path)
		dir.eachFileRecurse (FileType.FILES) { file ->
			if ((!file.isHidden() && !file.isDirectory()) && file.getName().endsWith(".savedSearch"))
				l << file
		}
		return l
	}
	
}

class External {
	private static final Logger logger = LoggerFactory.getLogger(PMS.class)
	
	/*
	 * Execute external command on system.
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
					if (!line.startsWith("Could not open smart folder")) {
						l.add(line)
					}
			}
		} catch (IOException e) {
			Plugin.logger.error("Cannot execute " + query.toString().replace(",", ""))
			Plugin.logger.error(e.message)
		}
		return l
	}
}