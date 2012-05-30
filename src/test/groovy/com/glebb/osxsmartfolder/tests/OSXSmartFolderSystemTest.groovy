package com.glebb.osxsmartfolder.tests

import groovy.mock.interceptor.StubFor
import net.pms.dlna.DLNAResource

import org.junit.After
import org.junit.Test

import static org.junit.Assert.*

import com.glebb.osxsmartfolder.External
import com.glebb.osxsmartfolder.OSXSmartFolderSystem
import com.glebb.osxsmartfolder.PlatformAdapter


class OSXSmartFolderSystemTest {
	
	@After
	public void tearDown() {
		PlatformAdapter.metaClass = null
		External.metaClass = null
	}
	
	@Test
	void rootFolderIsCreatedOnAllPlatforms() {
		PlatformAdapter.metaClass.'static'.isMac = { true }
		OSXSmartFolderSystem s = new OSXSmartFolderSystem()
		DLNAResource res = s.createFolderStructure()
		assertNotNull(res)
		
		PlatformAdapter.metaClass.'static'.isMac = { false }
		s = new OSXSmartFolderSystem()
		res = s.createFolderStructure()
		assertNotNull(res)
	}
	
	@Test
	void getListOfSmartFoldersNotExecutedOnOtherPlatformsThanMac() {
		PlatformAdapter.metaClass.'static'.isMac = { false }
		def mock = new StubFor(External)
		mock.demand.getListOfSmartFoldersFromFilesystem(0) { [] }
		mock.use { 
			OSXSmartFolderSystem s = new OSXSmartFolderSystem()
			DLNAResource res = s.createFolderStructure()
		}
	}
	
	@Test
	void subFoldersCreatedForSmartFolderFiles() {
		PlatformAdapter.metaClass.'static'.isMac = { true }
		External.metaClass.'static'.getListOfSmartFoldersFromFilesystem = {[new File("/tmp/test.savedSearch")]}
		OSXSmartFolderSystem s = new OSXSmartFolderSystem()
		DLNAResource res = s.createFolderStructure()
		assertEquals(1, res.childrenNumber())
	}
	
}
