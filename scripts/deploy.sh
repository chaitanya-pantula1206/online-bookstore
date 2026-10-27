#!/usr/bin/env bash
# Build the WAR with Maven and copy it into Tomcat's webapps folder.
# Usage:  CATALINA_HOME=/path/to/apache-tomcat-10.1.x ./scripts/deploy.sh
set -e
cd "$(dirname "$0")/.."

if [ -z "$CATALINA_HOME" ] || [ ! -d "$CATALINA_HOME/webapps" ]; then
    echo "Please set CATALINA_HOME to your Tomcat installation folder."
    exit 1
fi

if command -v mvn > /dev/null; then MVN=mvn; else MVN=./mvnw; fi
$MVN clean package

cp target/bookstore.war "$CATALINA_HOME/webapps/"
echo
echo "Deployed target/bookstore.war to $CATALINA_HOME/webapps/"
echo "Start Tomcat if it is not running:  $CATALINA_HOME/bin/startup.sh"
echo "Then open http://localhost:8080/bookstore/"
