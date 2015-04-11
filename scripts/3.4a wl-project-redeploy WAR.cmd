@ECHO OFF

CALL "%~dp0\env.cmd"

TITLE %PROJECT_NAME%: %~n0

ECHO.
ECHO.
ECHO.

SET CLASSPATH=%WLS_HOME%\lib\weblogic.jar

ECHO CLASSPATH=%CLASSPATH%.

IF NOT EXIST "%SOURCE_FOLDER%" GOTO PROJECT_NOT_FOUND

ECHO.
ECHO.
ECHO.

ECHO Deploying the file "%PACKAGE_WAR_FILENAME%"...
java weblogic.Deployer -adminurl t3://localhost:7001 -user %USERNAME% -password %PASSWORD% -redeploy -name %PACKAGE_WAR_NAME% -source "%PACKAGE_WAR_FILENAME%" -remote -upload


GOTO END

:PROJECT_NOT_FOUND

ECHO.
ECHO.
ECHO.
ECHO The project "%SOURCE_FOLDER%" was not found.

:END

ECHO.
ECHO.
ECHO.
PAUSE