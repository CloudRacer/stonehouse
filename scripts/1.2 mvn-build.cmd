REM Puts the correct persistence.xml in place.
REM Creates the Junction to provide a path to the source that does not include spaces.
REM Generated the correct WSDL for the host component.

@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

ECHO.
ECHO.
ECHO.

IF NOT EXIST "%SCRIPT_FOLDER_DEPLOYMENT_AREA%" (
	ECHO Creating the Junction "%SCRIPT_FOLDER_DEPLOYMENT_AREA%"...
	cmd.exe /C mklink /J "%SCRIPT_FOLDER_DEPLOYMENT_AREA%" "%SOURCE_FOLDER%"

	IF %ERRORLEVEL% neq 0 (
		ECHO Error %ERRORLEVEL% encountered. Exiting prematurely...
		GOTO EXIT
	)
	
	ECHO.
	ECHO.
	ECHO.
)

ECHO Replace persistence unit to work with war file
IF EXIST "%PERSISTENCE_FILE%" DEL "%PERSISTENCE_FILE%"
COPY "%SOURCE_FOLDER%\install\Eclipse\pu_war.xml" "%PERSISTENCE_FILE%"

ECHO.
ECHO.
ECHO.

ECHO Building the solution in the folder "%SOURCE_FOLDER%"...

CD "%SOURCE_FOLDER%"
CALL %BUILD_COMMAND_CLEAN_INSTALL%

PAUSE

GOTO EXIT

:EXIT
	
ECHO.
ECHO.
ECHO.

EXIT /B