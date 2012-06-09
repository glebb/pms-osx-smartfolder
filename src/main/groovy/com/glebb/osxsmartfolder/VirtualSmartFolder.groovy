package com.glebb.osxsmartfolder

import net.pms.dlna.RealFile
import net.pms.dlna.virtual.VirtualFolder


/*
 * VirtualFolder that delegates queries related to discovering
 * and refreshing childs to SmartFolder class.
 */
class VirtualSmartFolder extends VirtualFolder {
	
	private ResolveChildsDelegatee smartFolder
	
	public VirtualSmartFolder(String name, String thumbnailIcon) {
		super(name, thumbnailIcon)
		smartFolder = new ResolveChildsDelegatee(this, name)
	}

	@Override
	public void discoverChildren() {
		smartFolder.discoverChildren()
	}
	
	@Override
	public boolean refreshChildren() {
		return smartFolder.refreshChildren()
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
