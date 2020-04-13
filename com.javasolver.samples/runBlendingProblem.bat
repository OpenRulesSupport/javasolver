set CLASS_NAME=com.javasolver.samples.BlendingProblem
rem set SOLVER=Scip
set SOLVER=GLPK
rem set SOLVER=Coin
@echo off
cd %~dp0
call .\run
pause