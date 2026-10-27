#!/usr/bin/env bash
# ---------------------------------------------------------------------------
#  THE MANUAL WAY (the "before" picture)
#
#  This is what the team was doing by hand: compile every .java file,
#  copy the class files and web files into the right folders, hunt down the
#  library jars, and zip it all up into a WAR. Each step is a chance to
#  forget something. Compare it with:  mvn clean package
# ---------------------------------------------------------------------------
set -e
cd "$(dirname "$0")/.."

OUT=build/manual
STAGE=$OUT/bookstore
M2=${M2_REPO:-$HOME/.m2/repository}

echo "STEP 1: Locate the Servlet API jar needed to compile (by hand)"
if [ -n "$CATALINA_HOME" ] && [ -f "$CATALINA_HOME/lib/servlet-api.jar" ]; then
    SERVLET_API="$CATALINA_HOME/lib/servlet-api.jar"
else
    SERVLET_API="$M2/jakarta/servlet/jakarta.servlet-api/6.0.0/jakarta.servlet-api-6.0.0.jar"
fi
if [ ! -f "$SERVLET_API" ]; then
    echo "  ERROR: servlet-api.jar not found. Set CATALINA_HOME to your Tomcat folder."
    exit 1
fi
echo "  using $SERVLET_API"

echo "STEP 2: Clean the old output folder"
rm -rf "$OUT"
mkdir -p "$STAGE/WEB-INF/classes" "$STAGE/WEB-INF/lib"

echo "STEP 3: Compile all Java sources with javac"
javac --release 25 -encoding UTF-8 -cp "$SERVLET_API" -d "$STAGE/WEB-INF/classes" \
    $(find src/main/java -name '*.java')
echo "  compiled $(find "$STAGE/WEB-INF/classes" -name '*.class' | wc -l) class files"
echo "  (note: no unit tests were run!)"

echo "STEP 4: Copy JSPs, CSS and web.xml"
cp -r src/main/webapp/. "$STAGE/"

echo "STEP 5: Copy the JSTL library jars into WEB-INF/lib (by hand)"
JSTL_API="$M2/jakarta/servlet/jsp/jstl/jakarta.servlet.jsp.jstl-api/3.0.0/jakarta.servlet.jsp.jstl-api-3.0.0.jar"
JSTL_IMPL="$M2/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar"
for JAR in "$JSTL_API" "$JSTL_IMPL"; do
    if [ -f "$JAR" ]; then
        cp "$JAR" "$STAGE/WEB-INF/lib/"
    else
        echo "  WARNING: $(basename "$JAR") not found - the JSP pages will fail at runtime!"
    fi
done

echo "STEP 6: Zip everything into a WAR"
(cd "$STAGE" && jar -cf ../bookstore-manual.war .)

echo
echo "Done: $OUT/bookstore-manual.war"
echo "Six manual steps, hard-coded paths and versions, and no tests."
