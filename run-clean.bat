@echo off
REM JavaMT5 Clean Runner Script - Guarantees fresh build
REM
REM Usage: run-clean.bat <number> [sub-choice]
REM
REM Use this if run.bat fails with compilation errors
REM

REM Set console code page to UTF-8
chcp 65001 >nul

REM Full paths (modify if needed)
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.1.8-hotspot"
set "MVND=C:\Users\maven-mvnd-1.0.3-windows-amd64\bin\mvnd.cmd"

echo.
echo ============================================================
echo   CLEAN BUILD MODE
echo ============================================================
echo.

echo [1/5] Stopping Maven daemon...
call "%MVND%" --stop 2>nul
timeout /t 2 /nobreak >nul

echo [2/5] Killing any Java processes...
taskkill /F /IM java.exe 2>nul
taskkill /F /IM javaw.exe 2>nul
echo      Waiting for processes to terminate...
ping 127.0.0.1 -n 3 >nul

echo [3/5] Cleaning compiled files (preserving protoc dependencies)...
if exist target\classes (
    echo      Removing target\classes...
    rmdir /s /q target\classes 2>nul
)

if exist target\generated-sources (
    echo      Removing target\generated-sources...
    rmdir /s /q target\generated-sources 2>nul
)

if exist target\maven-status (
    echo      Removing target\maven-status...
    rmdir /s /q target\maven-status 2>nul
)

echo      Keeping target\protoc-dependencies (protobuf descriptors)
echo      Clean completed

echo [4/5] Rebuilding project...
call "%MVND%" compile

if errorlevel 1 (
    echo.
    echo ============================================================
    echo   BUILD FAILED!
    echo ============================================================
    echo.
    echo This usually happens because of file locking issues.
    echo.
    echo MANUAL FIX - Try these steps in order:
    echo.
    echo   1. Close VSCode/Eclipse/IntelliJ completely
    echo   2. Run: .\run-clean.bat %*
    echo.
    echo If still failing:
    echo   - Manually delete C:\Users\malin\JavaMT5\target
    echo   - Restart your computer
    echo.
    pause
    exit /b 1
)

echo [5/5] Running example %*...
echo.
if "%1"=="" (
    call "%MVND%" exec:java
) else (
    call "%MVND%" exec:java -Dexec.args="%*"
)
