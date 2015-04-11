@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

ECHO.
ECHO.
ECHO.

CD "%SOURCE_FOLDER%"

ECHO "Removing .settings folders ..."
FOR /d /r . %%d IN (.settings) DO @IF EXIST "%%d" RD /s/q "%%d"
ECHO "Removing project configuration (.project .classpath) ..."
FOR /d /r . %%d IN (.project .classpath) DO @IF EXIST "%%d" DEL /s "%%d"

ECHO.
ECHO.
ECHO.

PAUSE