FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -q clean package -DskipTests -Dmaven.compiler.release=17

FROM tomcat:10.1-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/bookstore.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
