set CLASS_NAME=com.javasolver.samples.BlendingProblem
rem set SOLVER=Scip
rem set SOLVER=GLPK
set SOLVER=Coin
@echo off
cd %~dp0
call .\run
pause