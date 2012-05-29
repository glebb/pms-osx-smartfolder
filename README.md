# OSX Smart Folder Plugin for Ps3 Media Server

OSX Smart Folders provide interface to Spotlight searches in Mac OS X. They are used to get easy access to specific types of files. 
You can create smart folders using Finder, by select new smart folder from the menu. While creating a  smart folder, you provide specific conditions. Based on your conditions, the files will show up in Finder.
When you save a smart folder, the default location is "Saved searches". It is located in ~/Library/Saved Searches. This is the folder which is used by OSX Smart Folder Plugin to show smart folder in media server. 
When plugin is active, there will be a new root folder and under that there is a folder for each smart folder, containing the files as they appear in Finder.

OSX Smart Folder Plugin is written in Groovy!

## Build
Prerequisities:
   * Gradle (build system)
   * PMS (1.5+) in local maven or gradle repository. 

To build, type: ``gradle``
Easiest way to get gradle is via [brew](http://mxcl.github.com/homebrew/) in OS X.
The build system will embed groovy to the jar.
You need to make sure you have pms version 1.5+ in your local maven or gradle repository. If you are able to compile PMS, just install it to local repo by: ``mvn install`` in PMS source root.
  
## Use
Place the compiled jar (from build/libs/) to plugins/ directory in PMS. 

You can download the ready-made [jar](http://www.displayofpatience.net/files/osxsmartfolders/) (ready to be used with current PMS)

## Todo / Ideas
  * Currently OSX Smart Folder Plugin supports only folders saved in the default location. User can however save the folders to any location. It would be possible to user a config file where user can define custom paths for smart folders, but it's not implemented yet.
  * Dynamic refreshing (adding new files)?

Copyright (c) 2012 Antti Niiles