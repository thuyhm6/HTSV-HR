@echo off
REM Compiles src/**/*.java into WebRoot/WEB-INF/classes and copies non-.java
REM resources (iBatis sqlmap XML, .properties, etc.) alongside them, mirroring
REM what the Eclipse/MyEclipse builder used to do automatically.
REM Debug info (-g) is required: this project's AOP config (aop:aspectj-autoproxy,
REM tx:advice pointcuts) relies on the local variable table to resolve advice
REM parameter bindings; without -g, Spring throws AmbiguousBindingException at startup.

setlocal

set "JDK_HOME=C:\Program Files\Java\jdk1.8.0_231"
set "PROJECT_DIR=%~dp0..\.."
set "SRC_DIR=%PROJECT_DIR%\src"
set "OUT_DIR=%PROJECT_DIR%\WebRoot\WEB-INF\classes"
set "LIB_DIR=%PROJECT_DIR%\WebRoot\WEB-INF\lib"
set "TOMCAT_LIB=D:\Java\apache-tomcat\lib"
set "SOURCES_FILE=%TEMP%\hanwha_htsv_sources.txt"

if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

dir /s /b "%SRC_DIR%\*.java" > "%SOURCES_FILE%"

echo Compiling Java sources...
"%JDK_HOME%\bin\javac.exe" -encoding UTF-8 -source 1.6 -target 1.6 -g -nowarn ^
  -d "%OUT_DIR%" ^
  -cp "%LIB_DIR%\*;%TOMCAT_LIB%\*" ^
  -sourcepath "%SRC_DIR%" ^
  "@%SOURCES_FILE%"

if errorlevel 1 (
  echo BUILD FAILED: javac reported errors.
  exit /b 1
)

echo Copying non-Java resources...
robocopy "%SRC_DIR%" "%OUT_DIR%" /E /XF *.java /NFL /NDL /NJH /NJS /NP >nul

if %errorlevel% geq 8 (
  echo BUILD FAILED: resource copy reported errors.
  exit /b 1
)

echo BUILD OK
exit /b 0
