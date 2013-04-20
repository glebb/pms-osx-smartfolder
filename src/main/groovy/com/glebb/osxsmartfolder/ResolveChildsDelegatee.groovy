package com.glebb.osxsmartfolder

import net.pms.PMS
import net.pms.dlna.RealFile

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/*
 * Receives queries from parent DLNAResource and deals with child objects. 
 */
class ResolveChildsDelegatee {

	private VirtualSmartFolder parent
	private List fileList
	private List updatedFileList
	private ISavedSearch savedSearch

	static final Logger logger = LoggerFactory.getLogger(PMS.class)
	
	public ResolveChildsDelegatee(VirtualSmartFolder resource, ISavedSearch savedSearch) {
		this.parent = resource
		this.savedSearch = savedSearch
	}
	
	/*
	 * Should be called from parents discoverChildren
	 */
	public void discoverChildren() {
		def command = Utils.getFileList(savedSearch)
		fileList = Utils.execute(command)
		addChildsToParent(fileList)
		parent.setLastRefreshTime(System.currentTimeMillis())
	}

	/*
	 * Should be called from parent
	 */
	public boolean refreshChildren() {
		if (System.currentTimeMillis() - parent.getLastRefreshTime() < 10000) return false;
		def command = Utils.getFileList(savedSearch)
		updatedFileList = Utils.execute(command)
		if (fileList as Set != updatedFileList as Set) {
			return true;
		}
		return false
	}
	
	
	public void doRefreshChildren() {
		addNewChildsToParent(updatedFileList)
		removeRemovedFilesFromParent(updatedFileList)
		fileList = updatedFileList
		parent.setLastRefreshTime(System.currentTimeMillis())
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
