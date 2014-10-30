#!/bin/sh
javac  -d target/classes src/main/java/com/eteam/studentregistration/*.java
returncode=$?

if [ "$returncode" != "1" ]; then
    java -classpath "target/classes" com.team.studentregistration.ProgramMain
fi
