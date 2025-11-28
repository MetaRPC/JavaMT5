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

REM Full paths (modify if needed)
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.1.8-hotspot"
set "MVND=C:\Users\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd"

REM Handle special commands
if "%1"=="stop" goto stop_daemon

REM Pass all arguments to Program.java
if "%1"=="" (
    "%MVND%" compile exec:java
) else (
    "%MVND%" compile exec:java -Dexec.args="%*"
)
goto end

:stop_daemon
echo Stopping Maven daemon...
"%MVND%" --stop
goto end

:end
