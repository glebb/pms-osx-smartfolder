package com.glebb.osxsmartfolder

import com.sun.jna.Platform

/*
 * Wraps Platfrom class for decoupling purposes.
 */
class PlatformProxy {
	static boolean isMac() {
		return Platform.isMac()	
	}
}
