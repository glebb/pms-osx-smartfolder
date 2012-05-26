# OSX Smart Folder Plugin for Ps3 Media Server

OSX Smart Folders provide interface to Spotlight searches in Mac OS X. They are used to get easy access to specific types of files. 
You can create smart folders using Finder, by select new smart folder from the menu. While creating a  smart folder, you provide specific conditions. Based on your conditions, the files will show up in Finder.
When you save a smart folder, the default location is "Saved searches". It is located in ~/Library/Saved Searches. This is the folder which is used by OSX Smart Folder Plugin to show smart folder in media server. 
When plugin is active, there will be a new root folder and under that there is a folder for each smart folder, containing the files as they appear in Finder.

OSX Smart Folder Plugin is written in Groovy!

## Build
Prerequisities: install groovy (and gradle if wanting to compile with that). Easiest way to get them via [brew](http://mxcl.github.com/homebrew/) in OS X

There is two ways to build OSX Smart Folder Plugin:
   1. Using build script: "./tojar.sh"
   2. Using gradle: "gradle"

tojar.sh will bundle necessary groovy files inside the jar, so the plugin can be used as is. Before you run the script, please edit the file and make necessary changes (set paths and dependencies).
Make sure you copy pms jar (e.g. pms-1.53.0-SNAPSHOT-jar-with-dependencies.jar) to lib/

Gradle will not include groovy, so if this option is used, groovy must be included in PMS (which is not the current state of PMS). With gradle you don't need to do any manual set up.
 
## Use
Place the compiled jar to plugins directory in PMS. Uncommenting and modifying the last part in tojar.sh will do this for you automatically (used for development).
You will want to use to jar compiled with tojar.sh with the official PS3 Media Server builds. The plugin jar without embedded groovy will not work.

## Todo
  * Currently OSX Smart Folder Plugin supports only folders saved in the default location. User can however save the folders to any location. It would be possible to user a config file where user can define custom paths for smart folders, but it's not implemented yet.
  * Refreshing (adding new files)?

Copyright (c) 2012 Antti Niiles