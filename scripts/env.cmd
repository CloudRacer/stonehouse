@ECHO OFF

SET INTERACTIVE=1
echo %cmdcmdline% | find /i "%ComSpec%" >nul
IF NOT errorlevel 1 SET INTERACTIVE=0

SET PROJECT_NAME=stonehouse
SET USERNAME=weblogic
SET PASSWORD=welcome1
SET SCRIPT_FOLDER=%~dp0
SET SOURCE_FOLDER=%SCRIPT_FOLDER%..
SET SCRIPT_FOLDER_DEPLOYMENT_AREA=%SCRIPT_FOLDER%..\..\..\..\..\%PROJECT_NAME%
:: Convert the relative path to an absolute path.
PUSHD "%SCRIPT_FOLDER_DEPLOYMENT_AREA%"
SET SCRIPT_FOLDER_DEPLOYMENT_AREA=%CD%
POPD
SET PROJECT_MAIN_FOLDER=%SOURCE_FOLDER%\wm6-%PROJECT_NAME%-components\wm6-%PROJECT_NAME%-main
SET PROJECT_DATA_FOLDER=%SOURCE_FOLDER%\wm6-%PROJECT_NAME%-data
SET DOMAIN_FOLDER=%WL_HOME%\..\user_projects\domains\%PROJECT_NAME%
SET INSTALL_SCRIPT_FOLDER=%SCRIPT_FOLDER_DEPLOYMENT_AREA%\install\Weblogic
SET PACKAGE_EAR_NAME=wm6-%PROJECT_NAME%-jcom-ear
SET PACKAGE_EAR_FILENAME=%SCRIPT_FOLDER_DEPLOYMENT_AREA%\%PACKAGE_EAR_NAME%\target\%PACKAGE_EAR_NAME%.ear
SET PACKAGE_WAR_NAME=wm6-%PROJECT_NAME%-war
SET PACKAGE_WAR_FILENAME=%SCRIPT_FOLDER_DEPLOYMENT_AREA%\%PACKAGE_WAR_NAME%\target\%PACKAGE_WAR_NAME%.war
SET PERSISTENCE_FILE=%SCRIPT_FOLDER_DEPLOYMENT_AREA%\wm6-%PROJECT_NAME%-components\wm6-%PROJECT_NAME%-main\src\main\resources\META-INF\persistence.xml
SET BUILD_PROGRAME=mvn
SET BUILD_COMMAND_CLEAN=%BUILD_PROGRAME% clean
SET BUILD_COMMAND_CLEAN_INSTALL=%BUILD_COMMAND_CLEAN% install -DskipTests
SET BUILD_COMMAND_SYSTEM_TEST=%BUILD_COMMAND_CLEAN% install -Pweblogicsystemtest
SET BUILD_COMMAND_SYSTEM_DATALOAD=%BUILD_PROGRAME% package -P loadData
SET BUILD_COMMAND_ECLIPSE_ECLIPSE=%BUILD_PROGRAME% eclipse:eclipse
SET EMULATOR_JUNCTION=%SCRIPT_FOLDER_DEPLOYMENT_AREA%\..\emulator

ECHO PROJECT_NAME:%PROJECT_NAME%.
ECHO USERNAME:%USERNAME%.
ECHO PASSWORD:%PASSWORD%.
ECHO SCRIPT_FOLDER:%SCRIPT_FOLDER%.
ECHO SOURCE_FOLDER:%SOURCE_FOLDER%.
ECHO SCRIPT_FOLDER_DEPLOYMENT_AREA:%SCRIPT_FOLDER_DEPLOYMENT_AREA%.
ECHO PROJECT_MAIN_FOLDER:%PROJECT_MAIN_FOLDER%.
ECHO PROJECT_DATA_FOLDER:%PROJECT_DATA_FOLDER%.
ECHO DOMAIN_FOLDER:%DOMAIN_FOLDER%.
ECHO INSTALL_SCRIPT_FOLDER:%INSTALL_SCRIPT_FOLDER%.
ECHO PACKAGE_EAR_NAME:%PACKAGE_EAR_NAME%.
ECHO PACKAGE_EAR_FILENAME:%PACKAGE_EAR_FILENAME%.
ECHO PACKAGE_WAR_NAME:%PACKAGE_WAR_NAME%.
ECHO PACKAGE_WAR_FILENAME:%PACKAGE_WAR_FILENAME%.
ECHO PERSISTENCE_FILE:%PERSISTENCE_FILE%.
ECHO BUILD_PROGRAME:%BUILD_PROGRAME%.
ECHO BUILD_COMMAND_CLEAN:%BUILD_COMMAND_CLEAN%.
ECHO BUILD_COMMAND_CLEAN_INSTALL:%BUILD_COMMAND_CLEAN_INSTALL%.
ECHO BUILD_COMMAND_SYSTEM_TEST:%BUILD_COMMAND_SYSTEM_TEST%.
ECHO BUILD_COMMAND_SYSTEM_DATALOAD:%BUILD_COMMAND_SYSTEM_DATALOAD%.
ECHO EMULATOR_JUNCTION:%EMULATOR_JUNCTION%.
ECHO BUILD_COMMAND_ECLIPSE_ECLIPSE:%BUILD_COMMAND_ECLIPSE_ECLIPSE%.