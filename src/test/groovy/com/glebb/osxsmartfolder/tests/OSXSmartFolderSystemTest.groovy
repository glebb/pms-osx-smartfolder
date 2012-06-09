package com.glebb.osxsmartfolder.tests

import groovy.mock.interceptor.StubFor
import net.pms.dlna.DLNAResource

import org.junit.After
import org.junit.Test

import static org.junit.Assert.*

import com.glebb.osxsmartfolder.Utils
import com.glebb.osxsmartfolder.OSXSmartFolderSystem
import com.glebb.osxsmartfolder.PlatformProxy


class OSXSmartFolderSystemTest {
	
	@After
	public void tearDown() {
		PlatformProxy.metaClass = null
		Utils.metaClass = null
	}
	
	@Test
	void rootFolderShouldBeCreatedOnAllPlatforms() {
		PlatformProxy.metaClass.static.isMac = { true }
		OSXSmartFolderSystem s = new OSXSmartFolderSystem()
		DLNAResource res = s.createFolderStructure()
		assertNotNull(res)
		
		PlatformProxy.metaClass.static.isMac = { false }
		s = new OSXSmartFolderSystem()
		res = s.createFolderStructure()
		assertNotNull(res)
	}
	
	@Test
	void getListOfSmartFoldersShouldNotBeExecutedOnOtherPlatformsThanMac() {
		PlatformProxy.metaClass.static.isMac = { false }
		def mock = new StubFor(Utils)
		mock.demand.getListOfSmartFoldersFromFilesystem(0) { "should not be called" }
		mock.use { 
			OSXSmartFolderSystem s = new OSXSmartFolderSystem()
			DLNAResource res = s.createFolderStructure()
		}
	}
	
	@Test
	void subFoldersShouldBeCreatedForSmartFolderFiles() {
		PlatformProxy.metaClass.static.isMac = { true }
		Utils.metaClass.static.getListOfSmartFoldersFromFilesystem = {[new File("/tmp/test.savedSearch")]}
		OSXSmartFolderSystem s = new OSXSmartFolderSystem()
		DLNAResource res = s.createFolderStructure()
		assertEquals(1, res.childrenNumber())
	}
	
}
