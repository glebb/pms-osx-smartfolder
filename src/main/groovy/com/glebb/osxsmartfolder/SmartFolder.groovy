package com.glebb.osxsmartfolder

import groovy.io.FileType

import java.awt.geom.Line2D;
import java.util.List;

import net.pms.PMS
import net.pms.dlna.DLNAResource
import net.pms.dlna.RealFile
import net.pms.dlna.virtual.VirtualFolder;

import org.apache.commons.io.FilenameUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.jna.Platform

class SmartFolderHelper {
	
	private static final MDFIND = "/usr/bin/mdfind"
	private static final Logger logger = LoggerFactory.getLogger(PMS.class)

	/*
	 * Returns a list of .savedSearch files found from the given path
	 */
	List getFolders(path) {
		def l = []
		def dir = new File(path)
		dir.eachFileRecurse (FileType.FILES) { file ->
			if ((!file.isHidden() && !file.isDirectory()) && file.getName().endsWith(".savedSearch"))
				l << file
		}
		return l
	}
	
	/*
	 * Add RealFile child nodes to given VirtualFolder root, based
	 * on query executed against Smart Folder (basename) 
	 * using osx external mdfind command.
	 */
	void addFilesForSmartFolder(VirtualFolder root, basename) {
		List list = External.execute([MDFIND, "-s", basename.toString()])
		for (item in list) {
			File f = new File(item)
			RealFile file = new RealFile(f)
			root.addChild(file)
		}
	}
	
}

class External {
	private static final Logger logger = LoggerFactory.getLogger(PMS.class)
	
	/*
	 * Execute external command on system.
	 * @param query List of string, first being the command to be executed,
	 * followed by parameters as items in list. Return empty list if command
	 * is not found.
	 */
	static List execute(List query) {
		def l = []
		if (query.isEmpty()) return l
		try {
			def process = query.execute()
			process.in.eachLine { line ->
					if (!line.startsWith("Could not open smart folder")) {
						l.add(line)
						println line
					}
			}
		} catch (IOException e) {
			logger.error("Cannot execute " + query.toString().replace(",", ""))
			logger.error(e.message)
		}
		return l
	}
}