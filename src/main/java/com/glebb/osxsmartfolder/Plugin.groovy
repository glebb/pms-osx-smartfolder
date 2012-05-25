package com.glebb.osxsmartfolder;

import java.util.List;

import javax.swing.JComponent

import net.pms.dlna.DLNAResource
import net.pms.dlna.MapFile
import net.pms.dlna.RealFile
import net.pms.dlna.virtual.VirtualFolder
import net.pms.external.AdditionalFolderAtRoot;

class Plugin implements AdditionalFolderAtRoot {

	public Plugin() {
	}

	public String name() {
	   return "OSX Smart Folder yeah"
	}

	@Override
	public JComponent config() {
		return null;
	}

	@Override
	public void shutdown() {
	}

	@Override
	public DLNAResource getChild() {
		List list = Mdfind.query()
		VirtualFolder vf = new VirtualFolder("osxsmartfolder", null)
		
		for (item in list) {
			File f = new File(item)
			RealFile file = new RealFile(f)
			vf.addChild(file)
		}
	
		return vf
	}
}

class Mdfind {
	
	public Mdfind() {
	}
	
	static List query() {
		def l = []
		def process = "mdfind _kMDItemGroupId = 7 && kMDItemFSName = \"*.mp4\" && true".execute()
		process.in.eachLine {
				line -> l.add(line)
		}
		return l
	}
}

class SmartFolderXml {
	public SmartFolderXml() {}
	def supportedMdItems = [
		"_kMDItemGroupId",
		"kMDItemDisplayName",
		"kMDItemFSContentChangeDate",
		"kMDItemFSCreationDate",
		"kMDItemFSExists",
		"kMDItemFSInvisible",
		"kMDItemFSIsExtensionHidden",
		"kMDItemFSIsReadable",
		"kMDItemFSIsWriteable",
		"kMDItemFSLabel",
		"kMDItemFSName",
		"kMDItemFSNodeCount",
		"kMDItemFSOwnerGroupID",
		"kMDItemFSOwnerUserID",
		"kMDItemFSSize",
		"kMDItemPath",
		"kMDItemAudiences",
		"kMDItemAuthors",
		"kMDItemAuthorAddresses",
		"kMDItemCity",
		"kMDItemComment",
		"kMDItemContactKeywords",
		"kMDItemContentType",
		"kMDItemContributors",
		"kMDItemCopyright",
		"kMDItemCountry",
		"kMDItemCoverage",
		"kMDItemCreator",
		"kMDItemDescription",
		"kMDItemDurationSeconds",
		"kMDItemEmailAddresses",
		"kMDItemEncodingApplications",
		"kMDItemFinderComment",
		"kMDItemFonts",
		"kMDItemHeadline",
		"kMDItemIdentifier",
		"kMDItemInstantMessageAddresses",
		"kMDItemInstructions",
		"kMDItemKeywords",
		"kMDItemKind",
		"kMDItemLanguages",
		"kMDItemNumberOfPages",
		"kMDItemOrganizations",
		"kMDItemPageHeight",
		"kMDItemPageWidth",
		"kMDItemParticipants",
		"kMDItemPhoneNumbers",
		"kMDItemProjects",
		"kMDItemPublishers",
		"kMDItemRecipients",
		"kMDItemRecipientAddresses",
		"kMDItemRights",
		"kMDItemSecurityMethod",
		"kMDItemStarRating",
		"kMDItemStateOrProvince",
		"kMDItemTextContent",
		"kMDItemTitle",
		"kMDItemVersion",
		"kMDItemWhereFroms",
		"kMDItemSupportFileType",
		"kMDItemAuthorEmailAddresses",
		"kMDItemRecipientEmailAddresses",
		"kMDItemTheme",
		"kMDItemSubject",
		"kMDItemCFBundleIdentifier",
		"kMDItemFSHasCustomIcon",
		"kMDItemFSIsStationery",
		"kMDItemInformation",
		"kMDItemURL",
		"kMDItemPixelHeight",
		"kMDItemPixelWidth",
		"kMDItemPixelCount",
		"kMDItemColorSpace",
		"kMDItemBitsPerSample",
		"kMDItemFlashOnOff",
		"kMDItemFocalLength",
		"kMDItemAcquisitionMake",
		"kMDItemAcquisitionModel",
		"kMDItemISOSpeed",
		"kMDItemOrientation",
		"kMDItemLayerNames",
		"kMDItemWhiteBalance",
		"kMDItemAperture",
		"kMDItemProfileName",
		"kMDItemResolutionWidthDPI",
		"kMDItemResolutionHeightDPI",
		"kMDItemExposureMode",
		"kMDItemExposureTimeSeconds",
		"kMDItemEXIFVersion",
		"kMDItemAlbum",
		"kMDItemHasAlphaChannel",
		"kMDItemRedEyeOnOff",
		"kMDItemMeteringMode",
		"kMDItemMaxAperture",
		"kMDItemFNumber",
		"kMDItemExposureProgram",
		"kMDItemExposureTimeString",
		"kMDItemEXIFGPSVersion",
		"kMDItemAltitude",
		"kMDItemLatitude",
		"kMDItemLongitude",
		"kMDItemTimestamp",
		"kMDItemSpeed",
		"kMDItemGPSTrack",
		"kMDItemImageDirection",
		"kMDItemNamedLocation",
		"kMDItemAudioBitRate",
		"kMDItemCodecs",
		"kMDItemDeliveryType",
		"kMDItemMediaTypes",
		"kMDItemStreamable",
		"kMDItemTotalBitRate",
		"kMDItemVideoBitRate",
		"kMDItemDirector",
		"kMDItemProducer",
		"kMDItemGenre",
		"kMDItemPerformers",
		"kMDItemOriginalFormat",
		"kMDItemOriginalSource",
		"kMDItemAppleLoopDescriptors",
		"kMDItemAppleLoopsKeyFilterType",
		"kMDItemAppleLoopsLoopMode",
		"kMDItemAppleLoopsRootKey",
		"kMDItemAudioChannelCount",
		"kMDItemAudioEncodingApplication",
		"kMDItemAudioSampleRate",
		"kMDItemAudioTrackNumber",
		"kMDItemComposer",
		"kMDItemIsGeneralMIDISequence",
		"kMDItemKeySignature",
		"kMDItemLyricist",
		"kMDItemMusicalGenre",
		"kMDItemMusicalInstrumentCategory",
		"kMDItemMusicalInstrumentName",
		"kMDItemRecordingDate",
		"kMDItemRecordingYear",
		"kMDItemTempo",
		"kMDItemTimeSignature"]
	
	public String parse(String xml) {
		def plist = new XmlParser().parseText(xml)
		String temp = plist.dict[0].string[0].text()
		return temp
	}
	
	public String createQuery(String data) {
		String query = ""
		for (type in this.supportedMdItems) {
			if (data.indexOf(type) != -1) {
				int ends = data.indexOf(')', data.indexOf(type))
				query += "&& " + data.substring(data.indexOf(type), ends)+ " "
			}
		}
		if (!query.isEmpty())
			return "true " + query.trim()
		return "";
		
	}
}

