package com.glebb.osxsmartfolder.tests

import java.util.List

import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

import org.junit.After;
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import com.glebb.osxsmartfolder.ISavedSearch
import com.glebb.osxsmartfolder.ResolveChildsDelegatee
import com.glebb.osxsmartfolder.VirtualSmartFolder
import com.glebb.osxsmartfolder.Utils
import com.glebb.osxsmartfolder.PlatformProxy

class ResolveChildsDelegateeTest {
	
	private ResolveChildsDelegatee resolveChildsDelegatee
	def fakeSavedSearch = new FakeSavedSearch()
	
	
	@After
	public void tearDown() {
		Utils.metaClass = null
	}
	
	private setReturnValueForExecute(List l) {
		Utils.metaClass.static.execute = {List command -> l }
	}

	@Test
	void itShouldNotAddResourceWhenThereIsNoFiles() {
		setReturnValueForExecute( [] )
		def mock = new MockFor(VirtualSmartFolder)
		mock.demand.with {
			addChild(0) { "should not be called" }
			setLastRefreshTime(1) {}
		}
		mock.use {
			def tempFile = new File("/tmp/tmp.txt")
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
		}
	}

	@Test
	void singleResourceShouldBeAddedToParent() {
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		def mock = new MockFor(VirtualSmartFolder)
		mock.demand.with {
			addChild(1) {}
			setLastRefreshTime(1) {}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
		} 
	}
	
	@Test
	void multipleResourcesShouldBeAddedToParent() {
		setReturnValueForExecute( ["/tmp/test.mp3", "/tmp/second.mp3"] )
		def mock = new MockFor(VirtualSmartFolder)
		mock.demand.with {
			addChild(2) {}
			setLastRefreshTime(1) {}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
		}
	}

	@Test
	void refreshShouldNotBeDoneWhenFilelistRemainsTheSame() {
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
		resolveChildsDelegatee.discoverChildren()
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		assertFalse(resolveChildsDelegatee.refreshChildren())
	}

	
	@Test
	void refreshShouldBeDoneWhenFilelistChanges() {
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		def vf = new VirtualSmartFolder("temp", fakeSavedSearch)
		resolveChildsDelegatee = new ResolveChildsDelegatee(vf, fakeSavedSearch)
		resolveChildsDelegatee.discoverChildren()
		setReturnValueForExecute( ["/tmp/blah.mp3"] )
		vf.setLastRefreshTime(0)
		assertTrue(resolveChildsDelegatee.refreshChildren())
	}

	@Test
	void removedFilesShouldBeRemovedOnRefresh() {
		String toBeRemoved = "/tmp/test.mp3"
		setReturnValueForExecute( [toBeRemoved, "/tmp/blah.mp3"] )
		def mock = new StubFor(VirtualSmartFolder)
		def receivedParameter = null

		mock.demand.with {
			addChild(100) {}
			getChildren(100) {}
			getLastRefreshTime(1) {0} 
			setLastRefreshTime(2) {}
			removeChildren(1) {List l -> receivedParameter = l;}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/blah.mp3"] )
			resolveChildsDelegatee.refreshChildren()
			resolveChildsDelegatee.doRefreshChildren()
		}
		assertEquals([toBeRemoved], receivedParameter)
	}
	
	@Test
	void newFilesShouldBeAddedOnRefresh() {
		String toBeAdded = "/tmp/test.mp3"
		setReturnValueForExecute( ["/tmp/blah.mp3"] )
		def mock = new StubFor(VirtualSmartFolder)
		mock.demand.with {
			addChild(2) {}
			getLastRefreshTime(1) {0}
			getChildren(1) {}
			setLastRefreshTime(2) {}
			
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/blah.mp3", toBeAdded] )
			resolveChildsDelegatee.refreshChildren()
			resolveChildsDelegatee.doRefreshChildren()
		}
	}
	
	@Test
	void filesShouldNotBeRemovedOnRefreshWhenAddingNewFiles() {
		String toBeAdded = "/tmp/test.mp3"
		setReturnValueForExecute( ["/tmp/blah.mp3"] )
		def mock = new StubFor(VirtualSmartFolder)
		def receivedParameter = null
		mock.demand.with {
			addChild(2) {}
			getChildren(100) {}
			getLastRefreshTime(1) {0}
			removeChildren(0) { "should not be called"}
			setLastRefreshTime(2) {}
			
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/blah.mp3", toBeAdded] )
			resolveChildsDelegatee.refreshChildren()
			resolveChildsDelegatee.doRefreshChildren()
		}
	}

	@Test
	void existingFilesShouldNotBeAddedAgainOnRefresh() {
		setReturnValueForExecute( ["/tmp/blah.mp3", "/tmp/test.mp3"] )
		def mock = new StubFor(VirtualSmartFolder)
		def receivedParameter = null
		mock.demand.with {
			addChild(2) {}
			getLastRefreshTime(1) {0}
			getChildren(100) {}
			setLastRefreshTime(2) {}
			
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", fakeSavedSearch), fakeSavedSearch)
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/test.mp3","/tmp/blah.mp3"]  )
			resolveChildsDelegatee.refreshChildren()
			resolveChildsDelegatee.doRefreshChildren()
		}
	}
}

class FakeSavedSearch implements ISavedSearch {

	private static data
	
	public FakeSavedSearch() {
		data = Fixtures.savedSearchWithMultipleScopePaths
	}
	
	@Override
	public String loadData() {
		return data
	}

	@Override
	public String getBaseName() {
		return "/tmp/test"
	}	
	
	public setData(newData) {
		data = newData
	}
	
}
