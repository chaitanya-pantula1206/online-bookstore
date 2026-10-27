@echo off
REM Build the WAR with Maven and copy it into Tomcat's webapps folder.
REM Usage:  set CATALINA_HOME=C:\apache-tomcat-10.1.x  then  scripts\deploy.bat
setlocal
cd /d "%~dp0.."

if "%CATALINA_HOME%"=="" goto :nohome
if not exist "%CATALINA_HOME%\webapps" goto :nohome

where mvn > nul 2>&1
if errorlevel 1 (set MVN=mvnw.cmd) else (set MVN=mvn)
call %MVN% clean package
if errorlevel 1 exit /b 1

copy /y target\bookstore.war "%CATALINA_HOME%\webapps\" > nul
echo.
echo Deployed target\bookstore.war to %CATALINA_HOME%\webapps\
echo Start Tomcat if it is not running:  "%CATALINA_HOME%\bin\startup.bat"
echo Then open http://localhost:8080/bookstore/
exit /b 0

:nohome
echo Please set CATALINA_HOME to your Tomcat installation folder.
exit /b 1
