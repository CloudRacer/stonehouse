#!/bin/sh

pushd ../
mvn eclipse:eclipse
popd

read -p "Press [Enter] key to start continue..."