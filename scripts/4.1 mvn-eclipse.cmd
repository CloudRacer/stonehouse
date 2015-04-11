REM Puts the correct persistence.xml in place.
REM Overwrites the org.eclipse.wst.common.component file of the WAR project.

@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

ECHO Building the solution in the folder "%SOURCE_FOLDER%"...

CD "%SOURCE_FOLDER%"
CALL %BUILD_COMMAND_ECLIPSE_ECLIPSE%

PAUSE

EXIT /B