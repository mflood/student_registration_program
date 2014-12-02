#!/bin/sh
mkdir -p target/classes
mkdir -p target/libs
javac  -d target/classes src/main/java/com/eteam/studentregistration/*.java
jar cfm target/libs/eteam.jar manifest.txt -C target/classes com/eteam/studentregistration/

