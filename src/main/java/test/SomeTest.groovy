package test

import org.junit.Test

import com.glebb.osxsmartfolder.SmartFolderXml

import mockit.*

import groovy.util.GroovyTestCase


class SmartFolderXmlTest extends GroovyTestCase {
    
	private checkCreateQuery(String query, String expected) {
		assertTrue(query.startsWith("true &&"))
		assertTrue(query.contains(expected))
	}
	
	@Test
	void test_createQueryShouldReturnEmptyWithEmptyData() {
		SmartFolderXml sfxml = new SmartFolderXml()
		String query = sfxml.createQuery("")
		assertEquals("", query);
    }
	
	@Test
	void test_createQueryShouldReturnFSName() {
		SmartFolderXml sfxml = new SmartFolderXml()
		String query = sfxml.createQuery('(kMDItemFSName = "*.mp4"c)))')
		checkCreateQuery(query, "kMDItemFSName = \"*.mp4\"c")
	}
	
	@Test
	void test_createQueryShouldReturnGroupIdAndFSNameCombined() {
		SmartFolderXml sfxml = new SmartFolderXml()
		String query = sfxml.createQuery('(((_kMDItemGroupId = 7) &amp;&amp; (kMDItemFSName = "*.mp4"c))) &amp;&amp; (true)')
		checkCreateQuery(query, "_kMDItemGroupId = 7")
		checkCreateQuery(query, "kMDItemFSName = \"*.mp4\"")
	}
	
	@Test
	void test_createQueryShouldReturnEmptyWithGarbageData() {
		SmartFolderXml sfxml = new SmartFolderXml()
		String query = sfxml.createQuery('(((_kMadsaDItemGroupId = 7) &ampsda;&amp; (kMDItsdasemFSName = "*.mp4"c))) &amp;&amp; (true)')
		assertEquals("", query);
	}


}

class XmlFixtures {
	static def movie_mp4 = 
	'''<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
	<plist version="1.0">
	<dict>
		<key>CompatibleVersion</key>
		<integer>1</integer>
		<key>RawQuery</key>
		<string>(((_kMDItemGroupId = 7) &amp;&amp; (kMDItemFSName = "*.mp4"c))) &amp;&amp; (true)</string>
		<key>RawQueryDict</key>
		<dict>
			<key>FinderFilesOnly</key>
			<true/>
			<key>RawQuery</key>
			<string>(((_kMDItemGroupId = 7) &amp;&amp; (kMDItemFSName = "*.mp4"c))) &amp;&amp; (true)</string>
			<key>SearchScopes</key>
			<array>
				<string>kMDQueryScopeComputer</string>
			</array>
			<key>UserFilesOnly</key>
			<true/>
		</dict>
		<key>SearchCriteria</key>
		<dict>
			<key>FXCriteriaSlices</key>
			<array>
				<dict>
					<key>criteria</key>
					<array>
						<string>kMDItemKind</string>
						<integer>300</integer>
						<integer>415</integer>
					</array>
					<key>displayValues</key>
					<array>
						<string>Kind</string>
						<string>is</string>
						<string>movies</string>
					</array>
					<key>rowType</key>
					<integer>0</integer>
					<key>subrows</key>
					<array/>
				</dict>
				<dict>
					<key>criteria</key>
					<array>
						<string>com_apple_FileExtensionAttribute</string>
						<integer>1900</integer>
						<integer>1901</integer>
					</array>
					<key>displayValues</key>
					<array>
						<string>File Extension</string>
						<string>is</string>
						<string>mp4</string>
					</array>
					<key>rowType</key>
					<integer>0</integer>
					<key>subrows</key>
					<array/>
				</dict>
			</array>
			<key>FXScope</key>
			<integer>1396929382</integer>
			<key>FXScopeArrayOfPaths</key>
			<array>
				<string>kMDQueryScopeComputer</string>
			</array>
		</dict>
		<key>SuggestedAttributes</key>
		<array/>
		<key>ViewSettings</key>
		<dict>
			<key>WindowState</key>
			<dict>
				<key>ShowPathbar</key>
				<true/>
				<key>ShowSidebar</key>
				<true/>
				<key>ShowStatusBar</key>
				<false/>
				<key>ShowToolbar</key>
				<true/>
				<key>SidebarWidth</key>
				<integer>151</integer>
				<key>WindowBounds</key>
				<string>{{488, 133}, {770, 438}}</string>
			</dict>
		</dict>
	</dict>
	</plist>
	'''
  }
