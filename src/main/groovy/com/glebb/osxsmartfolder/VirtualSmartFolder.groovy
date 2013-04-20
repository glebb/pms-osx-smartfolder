package com.glebb.osxsmartfolder

import net.pms.PMS
import net.pms.dlna.virtual.VirtualFolder

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/*
 * VirtualFolder that delegates queries related to discovering
 * and refreshing childs to SmartFolder class.
 */
class VirtualSmartFolder extends VirtualFolder {
	
	
	private ResolveChildsDelegatee smartFolder
	
	public VirtualSmartFolder(String thumbnailIcon, ISavedSearch savedSearch) {
		super(savedSearch.getBaseName(), thumbnailIcon)
		smartFolder = new ResolveChildsDelegatee(this, savedSearch)
	}

	@Override
	public void discoverChildren() {
		smartFolder.discoverChildren()
	}
	
	@Override
	public boolean refreshChildren() {
		if (smartFolder.refreshChildren()) {
			 doRefreshChildren();
			 return true;
		}
		return false;
	}
	
	@Override
	public void doRefreshChildren() {
		smartFolder.doRefreshChildren();
	}
	
	public void removeChildren(List toBeRemoved) {
		List removeRealFiles = []
		for (item in getChildren()) {
			for (item2 in toBeRemoved) {
				if (item2.contains(item.getName()))
					removeRealFiles.add(item)
			}
		}
		getChildren().removeAll(removeRealFiles)
	}
}
