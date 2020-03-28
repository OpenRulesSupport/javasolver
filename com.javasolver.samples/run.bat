if "%CLASS_NAME%" == "" set CLASS_NAME=com.javasolver.samples.ProblemZoo
if "%SOLVER%" == "" set SOLVER=Constrainer
rem call mvn -f pom%SOLVER%.xml compile exec:java -Dexec.mainClass=%CLASS_NAME% -q
call mvn -P %SOLVER% compile exec:java -Dexec.mainClass=%CLASS_NAME% -q
