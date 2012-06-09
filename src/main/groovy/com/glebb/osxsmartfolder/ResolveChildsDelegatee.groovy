package com.glebb.osxsmartfolder

import java.util.List;

import net.pms.dlna.RealFile


/*
 * Receives queries from parent DLNAResource and deals with child objects. 
 */
class ResolveChildsDelegatee {

	private VirtualSmartFolder parent
	private static final MDFIND = "/usr/bin/mdfind"
	private List fileList
	private String name

	
	public ResolveChildsDelegatee(VirtualSmartFolder resource, String name) {
		this.parent = resource
		this.name = name
	}
	
	/*
	 * Should be called from parents discoverChildren
	 */
	public void discoverChildren() {
		fileList = Utils.execute([MDFIND, "-s", name.toString()])
		addChildsToParent(fileList)
	}

	/*
	 * Should be called from parents refershChildren
	 */
	public boolean refreshChildren() {
		List updatedFileList = Utils.execute([MDFIND, "-s", name.toString()])
		if (fileList.equals(updatedFileList)) {
			return false
		}
		else {
			addNewChildsToParent(updatedFileList)
			removeRemovedFilesFromParent(updatedFileList)
			fileList = updatedFileList
		}
		return true
	}

	private removeRemovedFilesFromParent(List updatedFileList) {
		List removed = []
		for (item in fileList) {
			if (!updatedFileList.contains(item)) {
				removed.add(item)
			}
		}

		if (!removed.isEmpty()) {
			parent.removeChildren(removed)
		}
	}

	private addNewChildsToParent(List updatedFileList) {
		List added = []

		for (item in updatedFileList) {
			if (!fileList.contains(item)) {
				added.add(item)
			}
		}
		addChildsToParent(added)
	}	

	private addChildsToParent(List fileList) {
		if (!fileList.isEmpty()) {
			for (item in fileList) {
				File f = new File(item)
				RealFile file = new RealFile(f)
				parent.addChild(file)
			}
		}
	}
	

		
}
