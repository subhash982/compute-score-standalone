@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%
call mvnw package -Dmaven.test.skip=true
cls
java -jar target/compute-score-standalone.jar