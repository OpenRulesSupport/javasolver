set CLASS_NAME=com.javasolver.samples.InsideOutsideProductionVar
rem set SOLVER=SCIP
set SOLVER=CLP
rem set SOLVER=GLPK
rem set SOLVER=COIN
@echo off
cd %~dp0
call .\run
pause