@echo off
cls
set MAVEN_HOME=C:\Users\Eduardo\sft\apache-maven-3.6.3
set JAVA_HOME=C:\Users\Eduardo\sft\jdk-11.0.16

set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
set M2_HOME=%MAVEN_HOME%
call mvn clean package -Dmaven.test.skip=true
pause
