#!/bin/sh

JAR=application-1.0-SNAPSHOT.jar
MAIN_CLASS=uk.org.mcdonnell.stonehouse.Application
LIB_FOLDER=lib

jar xf $JAR $LIB_FOLDER
java -cp ./*;./$LIB_FOLDER/* $MAIN_CLASS
