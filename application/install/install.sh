#!/bin/sh

setScriptFilename() {
	SCRIPT_FILE=`basename $0`
	RESULT=$?
	if [ $RESULT -ne 0 ]; then
		echo "ERR: $RESULT: Error encountered while determining the name of the current script."
		return $RESULT;
	fi

	echo SCRIPT_FILE:$SCRIPT_FILE.

	return 0
}

setScriptFolderName() {
	SCRIPT_FOLDER=`dirname $0`;
	RESULT=$?
	if [ $RESULT -ne 0 ]; then
		echo "ERR: $RESULT: Error encountered while determining the name of the folder containing the current script."
		return $RESULT;
	fi

	if [ "$SCRIPT_FOLDER" = "" ] || [ "$SCRIPT_FOLDER" = "." ] || [ -z "$SCRIPT_FOLDER" ]; then
		SCRIPT_FOLDER=`pwd`
	fi

	echo SCRIPT_FOLDER:$SCRIPT_FOLDER.

	return 0
}

initialiseEnvironment() {
	setScriptFilename
	RESULT=$?
	if [ $RESULT -ne 0 ]; then
		return $RESULT
	fi

	setScriptFolderName
	RESULT=$?
	if [ $RESULT -ne 0 ]; then
		return $RESULT
	fi

	return 0
}

main() {
	initialiseEnvironment
	RESULT=$?
	if [ $RESULT -ne 0 ]; then
		return $RESULT
	fi

	return 0
}

main
RESULT=$?
if [ $RESULT -ne 0 ]; then
	return $RESULT
fi

APPLICATION_NAME=stonehouse
APPLICATION_DESTINATION_FOLDERNAME=/lib/systemd/system
APPLICATION_SERVICE_FILENAME=$APPLICATION_NAME.service
DAEMON_SCRIPT=$APPLICATION_NAME
CONF_FILE=$APPLICATION_NAME.conf

echo APPLICATION_NAME:$APPLICATION_NAME.
echo APPLICATION_DESTINATION_FOLDERNAME=$APPLICATION_DESTINATION_FOLDERNAME.
echo APPLICATION_SERVICE_FILENAME=$APPLICATION_SERVICE_FILENAME.
echo DAEMON_SCRIPT=$DAEMON_SCRIPT.
echo CONF_FILE=$CONF_FILE.

echo Installing $APPLICATION_NAME...

sudo cp $SCRIPT_FOLDER/$DAEMON_SCRIPT /etc/init.d/
sudo chmod 644 /etc/init.d/$DAEMON_SCRIPT

sudo cp $SCRIPT_FOLDER/$CONF_FILE /etc/init/
sudo chmod 644 /etc/init/$CONF_FILE

sudo cp $SCRIPT_FOLDER/$APPLICATION_SERVICE_FILENAME $APPLICATION_DESTINATION_FOLDERNAME
sudo chmod 644 $APPLICATION_DESTINATION_FOLDERNAME/$APPLICATION_SERVICE_FILENAME

sudo mkdir -p /opt/$APPLICATION_NAME
sudo cp $SCRIPT_FOLDER/application-1.0-SNAPSHOT.jar /opt/$APPLICATION_NAME

# Start when the server starts.
sudo update-rc.d $APPLICATION_NAME enable default

# Start stonehouse.
sudo service $APPLICATION_NAME start