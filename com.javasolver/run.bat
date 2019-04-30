@echo on
if "%CLASS_NAME%" == "" set CLASS_NAME=com.javasolver.samples.ProblemZoo
set LIB=../com.javasolver/lib/
set LOGLIB=%LIB%logging/
set LOGGING=%LOGLIB%commons-logging-1.1.jar;%LOGLIB%commons-logging-api-1.1.jar;%LOGLIB%log4j-1.2.15.jar
set JSRLIB=%LIB%jsr331
set JSR=%JSRLIB%/jsr331.jar
set CONSTRAINERLIB=%LIB%constrainer/jsr331.constrainer.jar;%LIB%constrainer/constrainer.light.jar
set SCIPLIB=%JSRLIB%/jsr331.linear.jar;%LIB%scip/jsr331.scip.jar
set COINLIB=%JSRLIB%/jsr331.linear.jar;%LIB%coin/jsr331.coin.jar
set GLPKLIB=%JSRLIB%/jsr331.linear.jar;%LIB%glpk/jsr331.glpk.jar
if "%SOLVER%" == "" set SOLVERLIB=%CONSTRAINERLIB%
if "%SOLVER%" == "CONSTRAINER" set SOLVERLIB=%CONSTRAINERLIB%
if "%SOLVER%" == "SCIP" set SOLVERLIB=%SCIPLIB%
if "%SOLVER%" == "COIN" set SOLVERLIB=%COINLIB%
if "%SOLVER%" == "GLPK" set SOLVERLIB=%GLPKLIB%
set ALL=./bin;%LIB%/javasolver/com.javasolver.jar;%LOGGING%;%JSR%;%SOLVERLIB%
java -Xmx2048m -classpath "%ALL%" %CLASS_NAME%
echo done
