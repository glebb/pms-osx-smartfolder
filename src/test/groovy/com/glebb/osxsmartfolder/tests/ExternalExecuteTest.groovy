package com.glebb.osxsmartfolder.tests


import org.junit.Before
import org.junit.Test
import static org.junit.Assume.*
import static org.junit.Assert.*

import com.glebb.osxsmartfolder.Utils
import com.sun.jna.Platform

class ExternalExecuteTest {
	/*
	 * Integration tests (executes real commands)
	 * Assumes OSX environment
	 */
	
	@Before
	void setUp() {
		assumeTrue Platform.isMac()
	}
	
	@Test
	void nonExistingCommandShouldReturnEmptyList() {
		def l = ["BLAH_this_should_not_be_found", "--help"]
		List temp = Utils.execute(l)
		assertTrue(temp.isEmpty())
	}
	
	@Test
	void emptyQueryShouldReturnEmptyList() {
		List temp = Utils.execute([])
		assertTrue(temp.isEmpty())
	}
		
	@Test
	void mdfindShouldExecuteeCorrectly() {
		List temp = Utils.execute(["mdfind", "--help"])
		assertTrue(temp.get(1).startsWith("Usage: mdfind"))
	}
		
}
