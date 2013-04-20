package com.glebb.osxsmartfolder.tests;

import static org.junit.Assert.*

import org.junit.Test

import com.glebb.osxsmartfolder.PlistUtility
import com.glebb.osxsmartfolder.Utils



class SavedSearchPlistTest {
	
	@Test
	void itFindsSinglePath()
	{
		def result = PlistUtility.parseXmlPlistText(Fixtures.savedSearchWithOneScopePath)
		assertEquals("/Users/antti/Movies", result.SearchCriteria['FXScopeArrayOfPaths'][0])
	}	

	@Test
	void itFindsAllPaths()
	{
		def result = PlistUtility.parseXmlPlistText(Fixtures.savedSearchWithMultipleScopePaths)
		assertEquals(2, result.SearchCriteria['FXScopeArrayOfPaths'].size())
	}

	@Test
	void itGeneratesCorrectMDFind()
	{
		def result = Utils.getFileList(new FakeSavedSearch());
		def expected = ["/usr/bin/mdfind", "-s", "/tmp/test", "-onlyin", "/Users/antti/Movies", "-onlyin", "/Users/antti/clips"]
		assertEquals(expected, result)
	}

	@Test
	void itHandlesQueryScopeComputer()
	{
		def fake = new FakeSavedSearch()
		fake.setData(Fixtures.savedSearchQueryScopeComputer)
		def result = Utils.getFileList(fake);
		def expected = ["/usr/bin/mdfind", "-s", "/tmp/test"]
		assertEquals(expected, result)
	}

}

