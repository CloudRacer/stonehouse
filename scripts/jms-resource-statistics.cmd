@ECHO OFF

CALL "%~dp0\env.cmd" > nul

TITLE %PROJECT_NAME%: %~n0
IF "%~1"=="" (
	CALL "%WL_HOME%\common\bin\wlst.cmd" "%SCRIPT_FOLDER%\jms-resource-statistics.py" | "%SCRIPT_FOLDER%\grep" --regex="Type.*Name\|-----\|Queue\|Topic"
) ELSE (
	CALL "%WL_HOME%\common\bin\wlst.cmd" "%SCRIPT_FOLDER%\jms-resource-statistics.py" | "%SCRIPT_FOLDER%\grep" --regex="Type.*Name\|-----\|%~1"
)

ECHO.
ECHO.
ECHO.

REM IF _%INTERACTIVE%_==_0_ PAUSE

EXIT /B