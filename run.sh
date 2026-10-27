#!/usr/bin/env bash
# ============================================================
#  JavaMT5 Runner (Linux / macOS / Git-Bash)
#  Compiles once, then launches examples directly via `java`.
#  Avoids the exec-maven-plugin classloader issue on new JDKs
#  and is much faster on repeated runs.
#
#  Usage:
#    ./run.sh          - show the interactive examples menu
#    ./run.sh 1        - Market Data (low-level)
#    ./run.sh 10 1     - Scalping orchestrator
#    ./run.sh rebuild  - clean build artifacts
# ============================================================
cd "$(dirname "$0")" || exit 1

JAVA="java"
[ -n "$JAVA_HOME" ] && JAVA="$JAVA_HOME/bin/java"

# classpath separator: ';' on Windows (incl. Git-Bash), ':' elsewhere
case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*) SEP=';' ;;
  *) SEP=':' ;;
esac

if [ "$1" = "rebuild" ]; then
  mvn -q clean; rm -f cp.txt; echo "[rebuild] cleaned."; exit 0
fi

# Build once if needed (protoc plugin is flaky, so retry)
if [ ! -f target/classes/Program.class ]; then
  echo "[setup] First run: compiling JavaMT5 (~30s; protoc may need one retry)..."
  mvn -q clean compile || mvn -q clean compile
fi
if [ ! -f target/classes/Program.class ]; then
  echo "[setup] BUILD FAILED - check that JDK and Maven are installed."; exit 1
fi

[ -f cp.txt ] || mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt

CP="target/classes${SEP}$(cat cp.txt)"
exec "$JAVA" -cp "$CP" Program "$@"
