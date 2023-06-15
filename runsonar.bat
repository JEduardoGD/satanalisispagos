@echo off
cls
set MAVEN_HOME=C:\Users\Eduardo\sft\apache-maven-3.6.3
set JAVA_HOME=C:\Users\Eduardo\sft\jdk-11.0.16

set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
set M2_HOME=%MAVEN_HOME%
mvn clean verify sonar:sonar -Dsonar.projectKey=descargopagoanalizer -Dsonar.projectName='descargopagoanalizer' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_55ee4e6104f6256a57ab496b3b2c81faa857a8e1
pause