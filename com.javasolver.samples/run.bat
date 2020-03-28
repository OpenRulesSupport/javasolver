rem @echo off
rem cd %~dp0
if "%CLASS_NAME%" == "" set CLASS_NAME=com.javasolver.samples.ProblemZoo
if "%SOLVER%" == "" set SOLVER=Constrainer
call mvn -f pom%SOLVER%.xml compile exec:java -Dexec.mainClass=%CLASS_NAME% -q
