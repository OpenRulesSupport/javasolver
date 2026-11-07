set CLASS_NAME=com.javasolver.samples.SendMoreMoney
rem set SOLVER=CONSTRAINER
set SOLVER=MAXICP
rem set SOLVER=CHOCO
rem set SOLVER=JSETL
@echo off
cd %~dp0
call .\run
pause