@echo off
if "%CLASS_NAME%" == "" set CLASS_NAME=com.javasolver.samples.ProblemZoo
set LIB=../com.javasolver/lib/
set LOGLIB=%LIB%logging/
set LOGGING=%LOGLIB%commons-logging-1.1.jar;%LOGLIB%commons-logging-api-1.1.jar;%LOGLIB%log4j-1.2.15.jar
set JSRLIB=%LIB%jsr331
set JSR=%JSRLIB%/jsr331.jar
rem set SOLVER=%LIB%constrainer/jsr331.constrainer.jar;%LIB%constrainer/constrainer.light.jar
set SOLVER=%JSRLIB%/jsr331.linear.jar;%LIB%scip/jsr331.scip.jar
rem set SOLVER=%JSRLIB%/jsr331.linear.jar;%LIB%coin/jsr331.coin.jar
rem set SOLVER=%JSRLIB%/jsr331.linear.jar;%LIB%glpk/jsr331.glpk.jar
set ALL=./bin;%LIB%com.javasolver.jar;%LOGGING%;%JSR%;%SOLVER%
java -Xmx2048m -classpath "%ALL%" %CLASS_NAME%
echo done
