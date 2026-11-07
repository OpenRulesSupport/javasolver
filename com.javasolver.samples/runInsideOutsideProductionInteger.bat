set CLASS_NAME=com.javasolver.samples.InsideOutsideProductionInteger
rem set SOLVER=SCIP
set SOLVER=CLP
rem set SOLVER=GLPK
rem set SOLVER=COIN
rem set SOLVER=OJALGO
@echo off
cd %~dp0
call .\run
pause