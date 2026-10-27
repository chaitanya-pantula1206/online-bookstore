@echo off
REM ---------------------------------------------------------------------------
REM  THE MANUAL WAY (the "before" picture)
REM
REM  Compile every .java file, copy class files and web files into the right
REM  folders, hunt down the library jars and zip it all into a WAR - by hand.
REM  Compare it with:  mvn clean package
REM ---------------------------------------------------------------------------
setlocal
cd /d "%~dp0.."

set OUT=build\manual
set STAGE=%OUT%\bookstore
if "%M2_REPO%"=="" set M2_REPO=%USERPROFILE%\.m2\repository

echo STEP 1: Locate the Servlet API jar needed to compile (by hand)
set SERVLET_API=%M2_REPO%\jakarta\servlet\jakarta.servlet-api\6.0.0\jakarta.servlet-api-6.0.0.jar
if not "%CATALINA_HOME%"=="" if exist "%CATALINA_HOME%\lib\servlet-api.jar" set SERVLET_API=%CATALINA_HOME%\lib\servlet-api.jar
if not exist "%SERVLET_API%" (
    echo   ERROR: servlet-api.jar not found. Set CATALINA_HOME to your Tomcat folder.
    exit /b 1
)
echo   using %SERVLET_API%

echo STEP 2: Clean the old output folder
if exist "%OUT%" rmdir /s /q "%OUT%"
mkdir "%STAGE%\WEB-INF\classes"
mkdir "%STAGE%\WEB-INF\lib"

echo STEP 3: Compile all Java sources with javac
dir /s /b src\main\java\*.java > "%OUT%\sources.txt"
javac --release 25 -encoding UTF-8 -cp "%SERVLET_API%" -d "%STAGE%\WEB-INF\classes" @"%OUT%\sources.txt"
if errorlevel 1 exit /b 1
echo   (note: no unit tests were run!)

echo STEP 4: Copy JSPs, CSS and web.xml
xcopy /e /i /q /y src\main\webapp "%STAGE%" > nul

echo STEP 5: Copy the JSTL library jars into WEB-INF\lib (by hand)
set JSTL_API=%M2_REPO%\jakarta\servlet\jsp\jstl\jakarta.servlet.jsp.jstl-api\3.0.0\jakarta.servlet.jsp.jstl-api-3.0.0.jar
set JSTL_IMPL=%M2_REPO%\org\glassfish\web\jakarta.servlet.jsp.jstl\3.0.1\jakarta.servlet.jsp.jstl-3.0.1.jar
if exist "%JSTL_API%" (copy /y "%JSTL_API%" "%STAGE%\WEB-INF\lib\" > nul) else (echo   WARNING: JSTL API jar not found - JSP pages will fail at runtime!)
if exist "%JSTL_IMPL%" (copy /y "%JSTL_IMPL%" "%STAGE%\WEB-INF\lib\" > nul) else (echo   WARNING: JSTL implementation jar not found - JSP pages will fail at runtime!)

echo STEP 6: Zip everything into a WAR
pushd "%STAGE%"
jar -cf ..\bookstore-manual.war .
popd

echo.
echo Done: %OUT%\bookstore-manual.war
echo Six manual steps, hard-coded paths and versions, and no tests.
endlocal
