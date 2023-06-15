@echo off
cls
set MAVEN_HOME=C:\Users\Eduardo\sft\apache-maven-3.6.3
set JAVA_HOME=C:\Users\Eduardo\sft\jdk-11.0.16

set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
set M2_HOME=%MAVEN_HOME%
mvn clean verify sonar:sonar -Dsonar.projectKey=descargopagoanalizer -Dsonar.projectName='descargopagoanalizer' -Dsonar.host.url=http://192.168.0.122:9000 -Dsonar.token=sqp_b3f4c1498221d5a03a1aa398af7b46487e5b7ea9
pause