@ECHO OFF

SET WEBLOGIC_CLIENT_JARS=%WL_HOME%\server\lib

ECHO.
ECHO.
ECHO.

ECHO WEBLOGIC_CLIENT_JARS:%WEBLOGIC_CLIENT_JARS%.

ECHO.
ECHO.
ECHO.

START "JVisual VM" /B "%JAVA_HOME%\bin\jvisualvm.exe" --cp:a %WEBLOGIC_CLIENT_JARS%\wlclient.jar;%WEBLOGIC_CLIENT_JARS%\wljmxclient.jar -J-Djmx.remote.protocol.provider.pkgs=weblogic.management.remote -J-Dcom.oracle.coherence.jvisualvm.disable.mbean.check=true