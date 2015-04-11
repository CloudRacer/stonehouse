@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

SET TEMP_FILENAME=%TEMP%\%RANDOM%

ECHO.
ECHO.
ECHO.

ECHO TEMP_FILENAME:"%TEMP_FILENAME%".

ECHO.
ECHO.
ECHO.

ECHO Fetching statistics...

:LOOP_FOREVER
CALL "%SCRIPT_FOLDER%\jms-resource-statistics.cmd" "%~1" > "%TEMP_FILENAME%"
CLS
TYPE "%TEMP_FILENAME%"
REM Pause for roughly 10 seconds
CALL "%SCRIPT_FOLDER%\pause.cmd"
ECHO.
ECHO Refreshing statistics...
GOTO LOOP_FOREVER

ECHO.
ECHO.
ECHO.

IF _%INTERACTIVE%_==_0_ PAUSE

EXIT /B