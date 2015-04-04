#!/bin/sh

mvn -B archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DgroupId=uk.org.mcdonnell.stonehouse \
  -DartifactId=stonehouse
