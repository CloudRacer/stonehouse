#!/bin/sh

mvn -B archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DgroupId=uk.org.mcdonnell.stonehouse \
  -DartifactId=plugins \
  -Dname=Plugins

read -p "Press [Enter] key to start continue..."