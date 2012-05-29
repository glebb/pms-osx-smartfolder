/*
* osxsmartfolders, a ps3mediaserver DLNA plugin for displaying OSX Smart Folders
* Copyright (C) 2012  Antti Niiles
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package com.glebb.osxsmartfolder;

import javax.swing.JComponent;

import org.apache.commons.io.FilenameUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.sun.jna.Platform

import net.pms.PMS
import net.pms.dlna.DLNAResource;
import net.pms.dlna.virtual.VirtualFolder;
import net.pms.external.AdditionalFolderAtRoot;

class Plugin implements AdditionalFolderAtRoot {

	private SmartFolderHelper sfHelper

	static final Logger logger = LoggerFactory.getLogger(PMS.class)
	
	public static final NAME = "OSX Smart Folders Plugin "
	public static final VERSION = "1.0.1"
	
	private static final SAVED_SEARCHES_FOLDER = System.getenv()['HOME'] + "/Library/Saved Searches/"
	
	
	public Plugin() {
		logger.info("Loading "  + NAME+ " " + VERSION)
		sfHelper = new SmartFolderHelper()
	}

	public String name() {
	   return NAME + VERSION
	}

	@Override
	public JComponent config() {
		return null;
	}

	@Override
	public void shutdown() {
	}

	@Override
	public DLNAResource getChild() {
		return createSmartFolderStructure()
	}
	
	private DLNAResource createSmartFolderStructure() {
		VirtualFolder vf = new VirtualFolder("OSX Smart Folders", null)
		if (Platform.isMac()) {
			createSmartFolders(vf);
		}
		else {
			logger.error(Plugin.NAME+": not running on OSX platform.")
		}
		return vf

	}
	
	private void createSmartFolders(VirtualFolder rootVf) {
		List smart_folders = sfHelper.getSmartFoldersFromFileSystem(SAVED_SEARCHES_FOLDER)
		if (!smart_folders.isEmpty()) {
			smart_folders.each {
				def basename = FilenameUtils.getBaseName(it.name)
				VirtualOSXSmartFolder subVf = new VirtualOSXSmartFolder(basename, null)
				rootVf.addChild(subVf)
			}
		}
		else {
			logger.info(NAME+": No Smart Folders found.")
		}
	}

}