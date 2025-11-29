@echo off
REM Generate Javadoc HTML documentation for JavaMT5 API
REM Output: target/site/apidocs/index.html

echo ========================================
echo Generating JavaMT5 API Documentation
echo ========================================
echo.

REM Check if mvnd is available, fallback to mvn
where mvnd >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Using Maven Daemon (mvnd)...
    mvnd javadoc:javadoc
) else (
    echo Using Maven (mvn)...
    mvn javadoc:javadoc
)

echo.
if %ERRORLEVEL% EQU 0 (
    echo ========================================
    echo SUCCESS! Documentation generated
    echo ========================================
    echo.
    echo Location: target\site\apidocs\index.html
    echo.
    echo Opening in browser...
    start target\site\apidocs\index.html
) else (
    echo ========================================
    echo ERROR: Documentation generation failed
    echo ========================================
    pause
)
