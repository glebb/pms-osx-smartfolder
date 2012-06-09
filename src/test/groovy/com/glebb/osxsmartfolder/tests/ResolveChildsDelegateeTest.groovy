package com.glebb.osxsmartfolder.tests

import java.util.List

import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

import org.junit.After;
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import com.glebb.osxsmartfolder.ResolveChildsDelegatee
import com.glebb.osxsmartfolder.VirtualSmartFolder
import com.glebb.osxsmartfolder.Utils
import com.glebb.osxsmartfolder.PlatformProxy

class ResolveChildsDelegateeTest {
	
	private ResolveChildsDelegatee resolveChildsDelegatee
	
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
		mock.demand.addChild(0) { "should not be called" }
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
		}
	}

	@Test
	void singleResourceShouldBeAddedToParent() {
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		def mock = new MockFor(VirtualSmartFolder)
		mock.demand.addChild(1) {}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
		} 
	}
	
	@Test
	void multipleResourcesShouldBeAddedToParent() {
		setReturnValueForExecute( ["/tmp/test.mp3", "/tmp/second.mp3"] )
		def mock = new MockFor(VirtualSmartFolder)
		mock.demand.addChild(2) {}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
		}
	}

	@Test
	void refreshShouldNotBeDoneWhenFilelistRemainsTheSame() {
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
		resolveChildsDelegatee.discoverChildren()
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		assertFalse(resolveChildsDelegatee.refreshChildren())
	}

	
	@Test
	void refreshShouldBeDoneWhenFilelistChanges() {
		setReturnValueForExecute( ["/tmp/test.mp3"] )
		resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
		resolveChildsDelegatee.discoverChildren()
		setReturnValueForExecute( ["/tmp/blah.mp3"] )
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
			removeChildren(1) {List l -> receivedParameter = l;}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/blah.mp3"] )
			resolveChildsDelegatee.refreshChildren()
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
			getChildren(1) {}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/blah.mp3", toBeAdded] )
			resolveChildsDelegatee.refreshChildren()
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
			removeChildren(0) { "should not be called"}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/blah.mp3", toBeAdded] )
			resolveChildsDelegatee.refreshChildren()
		}
	}

	@Test
	void existingFilesShouldNotBeAddedAgainOnRefresh() {
		setReturnValueForExecute( ["/tmp/blah.mp3", "/tmp/test.mp3"] )
		def mock = new StubFor(VirtualSmartFolder)
		def receivedParameter = null
		mock.demand.with {
			addChild(2) {}
			getChildren(100) {}
		}
		mock.use {
			resolveChildsDelegatee = new ResolveChildsDelegatee(new VirtualSmartFolder("temp", null), "temp")
			resolveChildsDelegatee.discoverChildren()
			setReturnValueForExecute(  ["/tmp/test.mp3","/tmp/blah.mp3"]  )
			resolveChildsDelegatee.refreshChildren()
		}
	}
}
