set CLASS_NAME=com.javasolver.samples.ScheduleAlternativeResources
rem set SOLVER=CONSTRAINER
set SOLVER=MAXICP
set SOLVER=CHOCO
rem set SOLVER=JSETL
@echo off
cd %~dp0
call .\run
pause