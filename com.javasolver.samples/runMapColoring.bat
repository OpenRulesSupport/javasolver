set CLASS_NAME=com.javasolver.samples.MapColoring
rem set SOLVER=CONSTRAINER
rem set SOLVER=SUGAR
set SOLVER=CHOCO
rem set SOLVER=JSETL
@echo off
cd %~dp0
call .\run
pause