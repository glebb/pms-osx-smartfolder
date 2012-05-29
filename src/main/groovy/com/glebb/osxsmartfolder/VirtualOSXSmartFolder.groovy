package com.glebb.osxsmartfolder

import net.pms.dlna.DLNAResource
import net.pms.dlna.RealFile
import net.pms.dlna.virtual.VirtualFolder

class VirtualOSXSmartFolder extends VirtualFolder {
	
	private static final MDFIND = "/usr/bin/mdfind"
	private List fileList
	
	public VirtualOSXSmartFolder(String name, String thumbnailIcon) {
		super(name, thumbnailIcon)
		
	}

	@Override
	public void discoverChildren() {
		fileList = addFilesForSmartFolder()
		for (item in fileList) {
			File f = new File(item)
			RealFile file = new RealFile(f)
			addChild(file)
		}
	}

	/*
	* List RealFile child nodes based
	* on query executed against Smart Folder name
	* using osx external mdfind command.
	*/
   private List addFilesForSmartFolder() {
	   List list = External.execute([MDFIND, "-s", name.toString()])
	   for (item in list) {
		   File f = new File(item)
		   RealFile file = new RealFile(f)
	   }
	   return list
   }

	//TODO: override: refreshChildren, doRefreshChildren, isRefreshNeeded	
}
