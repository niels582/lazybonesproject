#!/bin/bash
set -x
rm -rf build || true
rm -rf my-java-app || true
#NAMETEMPLATE="mvnjava"
#VERSIONTEMPLATE="1.0"

gradle clean

NAMETEMPLATE="gradlejava"
VERSIONTEMPLATE="1.0"

FILEZIP="$NAMETEMPLATE-template-$VERSIONTEMPLATE.zip"
USERBINTRAY="niels58"
KEYBINTRAY="b089fdb7d14138cbe431b4decc39cbe8b2e5321d"
REPOBINTRAY="lazybonestest"
FILEBINTRAY="work"
BASEAPIBINTRAY="api.bintray.com/content"

URLREPOBINTRAY="https://$BASEAPIBINTRAY/$USERBINTRAY/$REPOBINTRAY/$FILEBINTRAY/$VERSIONTEMPLATE/$FILEZIP"
gradle installAllTemplates --stacktrace --debug --info

cd build/packages && curl -T $FILEZIP \
  -u$USERBINTRAY:$KEYBINTRAY \
   $URLREPOBINTRAY

lazybones config add bintrayRepositories "niels58/lazybonestest/work"
lazybones config add bintrayRepositories "pledbrook/lazybones-templates"

#lazybones create mvnjava 1.0 my-java-app

#lazybones create gradlejava 1.0 my-java-app