#!/usr/bin/env bash

rm -fr out-linus

#java -jar packr-all-4.0.0.jar --platform mac --jdk /usr/lib/jvm/java-8-openjdk-amd64/bin/java --useZgcIfSupportedOs --executable robotGame --classpath robotGame-1-0.jar --mainclass com.mygdx.game.DesktopLauncher --vmargs -Xmx1G -XstartOnFirstThread --output out-mac

#linus
java -jar packr-all-4.0.0.jar --platform linux64 --jdk /usr/lib/jvm/java-8-openjdk-amd64/ --executable robotGame --mainclass com.mygdx.game.DesktopLauncher --output out-linus --classpath robotGame-1-0.jar

rm -fr out-mac

#mac?
java -jar packr-all-4.0.0.jar --platform mac --jdk /usr/lib/jvm/java-8-openjdk-amd64/ --executable robotGame --mainclass com.mygdx.game.DesktopLauncher --output out-mac --classpath robotGame-1-0.jar

rm -fr out-win


#win
java -jar packr-all-4.0.0.jar --platform windows64 --jdk /usr/lib/jvm/java-8-openjdk-amd64/ --executable robotGame --mainclass com.mygdx.game.DesktopLauncher --output out-win --classpath robotGame-1-0.jar

