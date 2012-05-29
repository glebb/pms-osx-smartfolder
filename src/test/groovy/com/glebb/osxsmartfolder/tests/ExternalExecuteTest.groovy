package com.glebb.osxsmartfolder.tests
import groovy.mock.interceptor.StubFor
import groovy.util.GroovyTestCase;


import net.pms.dlna.virtual.VirtualFolder

import org.junit.Test;

import com.glebb.osxsmartfolder.External
import com.glebb.osxsmartfolder.SmartFolderHelper



class ExternalExecuteTest extends GroovyTestCase {
	
	@Test
	void test_nonExistingCommandReturnsEmptyList() {
		def l = ["BLAH_this_should_not_be_found", "--help"]
		List temp = External.execute(l) //Actually executes the command
		assertTrue(temp.isEmpty())
	}
	
	@Test
		void test_emptyQueryReturnEmptyList() {
		List temp = External.execute([])
		assertTrue(temp.isEmpty())
	}
		
}

