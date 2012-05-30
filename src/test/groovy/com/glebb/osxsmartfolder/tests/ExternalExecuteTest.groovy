package com.glebb.osxsmartfolder.tests


import org.junit.Test
import static org.junit.Assert.*

import com.glebb.osxsmartfolder.External

class ExternalExecuteTest {
	/*
	 * Integration tests (executes real commands)
	 */
	@Test
	void nonExistingCommandReturnsEmptyList() {
		def l = ["BLAH_this_should_not_be_found", "--help"]
		List temp = External.execute(l)
		assertTrue(temp.isEmpty())
	}
	
	@Test
	void emptyQueryReturnEmptyList() {
		List temp = External.execute([])
		assertTrue(temp.isEmpty())
	}
		
	@Test
	void mdfindExecutesCorrectly() {
		List temp = External.execute(["mdfind", "--help"])
		assertTrue(temp.get(1).startsWith("Usage: mdfind"))
	}
		
}
