set CLASS_NAME=com.javasolver.samples.BlendingProblem
rem set SOLVER=SCIP
rem set SOLVER=GLPK
rem set SOLVER=COIN
set SOLVER=CLP
set SOLVER=OJALGO

@echo off
cd %~dp0
call .\run
pause