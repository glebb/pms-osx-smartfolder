#!/bin/bash
export GROOVY_HOME=/usr/local/Cellar/groovy/1.8.6/libexec/
cd src/main/java
rm com/glebb/osxsmartfolder/*.class
groovyc -cp ../../../lib/pms-1.53.0-SNAPSHOT-jar-with-dependencies.jar com/glebb/osxsmartfolder/*.groovy
groovy -cp ../../../lib/pms-1.53.0-SNAPSHOT-jar-with-dependencies.jar ../../../GroovyWrapper -m com/glebb/osxsmartfolder/Plugin -d ../../../dist/osxsmartfolders.jar
cd ../../../
cp dist/osxsmartfolders.jar "../glebb/src/main/external-resources/plugins/"    
