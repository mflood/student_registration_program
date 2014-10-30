#!/bin/sh
javac  -d target/classes src/main/java/com/eteam/studentregistration/*.java
jar cfm target/libs/eteam.jar manifest.txt -C target/classes com/eteam/studentregistration/

