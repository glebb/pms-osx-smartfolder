#!/bin/bash

#Setup (absolute paths might be a good idea)
export GROOVY_HOME=/usr/local/Cellar/groovy/1.8.6/libexec/
PMSJAR=../../../lib/pms-1.53.0-SNAPSHOT-jar-with-dependencies.jar
DEPLOYDIR=../glebb/src/main/external-resources/plugins/ 

#Clean
mkdir -p dist
cd src/main/groovy
rm -f com/glebb/osxsmartfolder/*.class

#Compile
groovyc -cp $PMSJAR com/glebb/osxsmartfolder/*.groovy

#Create jar with Groovy wrapped in
groovy -cp $PMSJAR ../../../GroovyWrapper -m com/glebb/osxsmartfolder/Plugin -d ../../../dist/osxsmartfolders-jar-with-groovy-1.0.0.jar

#Deploy
cd ../../../
cp dist/osxsmartfolders-jar-with-groovy-1.0.0.jar $DEPLOYDIR    

