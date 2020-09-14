set CLASS_NAME=com.javasolver.samples.BlendingProblem
set SOLVER=Scip
rem set SOLVER=GLPK
rem set SOLVER=Coin
rem set SOLVER=CLP
@echo off
cd %~dp0
call .\run
pause