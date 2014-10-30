#!/bin/sh
javac *.java
returncode=$?

if [ "$returncode" != "1" ]; then
    java -classpath "." ProgramMain
fi
