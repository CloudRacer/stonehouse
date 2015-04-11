@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

ECHO oracleLive|"%SCRIPT_FOLDER%\3.1 wl-project-create-domain.cmd"

GOTO END

:END

ECHO.
ECHO.
ECHO.
IF _%INTERACTIVE%_==_0_ PAUSE