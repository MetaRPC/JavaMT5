@echo off
REM JavaMT5 Runner Script
REM
REM Usage: run.bat <number> [sub-choice]
REM
REM Examples:
REM   run.bat 1         - Market Data (low-level)
REM   run.bat 7         - Simple Trading (sugar)
REM   run.bat 10 1      - Scalping orchestrator
REM   run.bat 11 1      - Aggressive Growth preset
REM
REM Special commands:
REM   run.bat stop      - Stop Maven daemon

REM Set console code page to UTF-8
chcp 65001 >nul

REM Set JAVA_HOME if not defined
if not defined JAVA_HOME (
    if exist "C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot" (
        set "JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot"
    )
)

REM Find Maven executable
set "MVN_EXEC=mvn"
if exist "C:\tools\apache-maven-3.9.9\bin\mvn.cmd" (
    set "MVN_EXEC=C:\tools\apache-maven-3.9.9\bin\mvn.cmd"
) else if exist "C:\Users\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd" (
    set "MVN_EXEC=C:\Users\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd"
)

REM Handle special commands
if "%1"=="stop" goto stop_daemon

REM Pass all arguments to Program.java
if "%1"=="" (
    call "%MVN_EXEC%" compile exec:java
) else (
    call "%MVN_EXEC%" compile exec:java -Dexec.args="%*"
)
goto end

:stop_daemon
echo Stopping Maven...
call "%MVN_EXEC%" --stop 2>nul
goto end

:end
