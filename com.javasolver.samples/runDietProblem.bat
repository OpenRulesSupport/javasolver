set CLASS_NAME=com.javasolver.samples.DietProblem
rem set SOLVER=Scip
rem set SOLVER=GLPK
rem set SOLVER=Coin
rem set SOLVER=CLP
set SOLVER=ojAlgo
rem set SOLVER=Constrainer
@echo off
cd %~dp0
call .\run
pause