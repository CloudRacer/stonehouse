@ECHO OFF

CALL "%~dp0\env.cmd"

ECHO.
ECHO.
ECHO.

SET CLASSPATH=%WLS_HOME%\lib\weblogic.jar

ECHO CLASSPATH=%CLASSPATH%.

ECHO.
ECHO.
ECHO.

ECHO Undeploying the package "%PACKAGE_EAR_NAME%"...
java weblogic.Deployer -adminurl t3://localhost:7001 -user %USERNAME% -password %PASSWORD% -undeploy -name %PACKAGE_EAR_NAME%

ECHO.
ECHO.
ECHO.
PAUSE