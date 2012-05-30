package com.glebb.osxsmartfolder.tests

import groovy.mock.interceptor.StubFor
import net.pms.dlna.DLNAResource;

import org.junit.After;
import org.junit.Test

import static org.junit.Assert.*

import com.glebb.osxsmartfolder.External
import com.glebb.osxsmartfolder.PlatformAdapter
import com.glebb.osxsmartfolder.VirtualOSXSmartFolder

class SubVirtualOSXSmartFolder extends VirtualOSXSmartFolder {

	List childs = []
	
	public SubVirtualOSXSmartFolder(String name, String thumbnailIcon) {
		super(name, thumbnailIcon)
	}

	@Override
	public void addChild(DLNAResource child) {
		childs.add(child)
	}
	
}

class VirtualOSXSmartFolderTest {
	
	@After
	public void tearDown() {
		PlatformAdapter.metaClass = null
		External.metaClass = null
	}

	@Test
	void resourcesCreatedBasedOnFilenames() {
		def mock = new StubFor(External)
		mock.demand.execute(1) { ["/tmp/test.mp3"] }
		mock.use {
			SubVirtualOSXSmartFolder vf = new SubVirtualOSXSmartFolder("temp", null)
			vf.discoverChildren()
			assertEquals(1, vf.childs.size())
			assertEquals("test.mp3", vf.childs.get(0).getName())
		}
	}	
}
