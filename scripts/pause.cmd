@ECHO OFF

SETLOCAL EnableDelayedExpansion
for /F "tokens=1,2 delims=#" %%a in ('"prompt #$H#$E# & echo on & for %%b in (1) do rem"') do (
  set "DEL=%%a"
)

SET COUNTDOWN_TEXT_COLOUR=0a

CALL :EVALUATE "chr(8)"
SET BACKSPACE=%result%
<nul set /p =Refreshing in 
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "10"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "9"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "8"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "7"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "6"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "5"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "4"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "3"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "2"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
CALL :ColourText %COUNTDOWN_TEXT_COLOUR% "1"
<nul set /p =...
:: Pause for approximately 1 second
ping localhost > nul
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
<nul set /p =10 second pause complete.
<nul set /p =%BACKSPACE%%BACKSPACE%%BACKSPACE%%BACKSPACE%
GOTO:EOF

:ColourText
::http://stackoverflow.com/questions/7923948/batch-color-per-line
echo off
<nul set /p ".=%DEL%" > "%~2"
findstr /v /a:%1 /R "^$" "%~2" nul
del "%~2" > nul 2>&1
goto :eof

:EVALUATE           -- evaluate with VBS and return to result variable
::                  -- %~1: VBS string to evaluate
:: extra info: http://groups.google.com/group/alt.msdos.batch.nt/browse_thread/thread/9092aad97cd0f917

@IF [%1]==[] ECHO Input argument missing & GOTO :EOF 

@ECHO wsh.echo "result="^&eval("%~1") > %temp%\evaluate_tmp_67354.vbs 
@FOR /f "delims=" %%a IN ('cscript //nologo %temp%\evaluate_tmp_67354.vbs') do @SET "%%a" 
@DEL %temp%\evaluate_tmp_67354.vbs
::ECHO %result%
@GOTO:EOF