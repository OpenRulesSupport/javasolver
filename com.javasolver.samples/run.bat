if "%CLASS_NAME%" == "" set CLASS_NAME=com.javasolver.samples.ProblemZoo
if "%SOLVER%" == "" set SOLVER=Constrainer
rem mvnDebug -Dsolver=%SOLVER% compile exec:java -Dexec.mainClass=%CLASS_NAME% -q
call mvn -Dsolver=%SOLVER% compile exec:java -Dexec.mainClass=%CLASS_NAME% -q
