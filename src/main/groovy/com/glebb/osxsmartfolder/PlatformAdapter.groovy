package com.glebb.osxsmartfolder

import com.sun.jna.Platform

class PlatformAdapter {
	static boolean isMac() {
		return Platform.isMac()	
	}
}
