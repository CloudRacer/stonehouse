@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

ECHO.
ECHO.
ECHO.

CALL "%WL_HOME%\common\bin\wlst.cmd" "%SCRIPT_FOLDER%\jms-resource-message-list.py"

ECHO.
ECHO.
ECHO.

IF _%INTERACTIVE%_==_0_ PAUSE

EXIT /B