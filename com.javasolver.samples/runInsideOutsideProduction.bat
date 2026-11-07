set CLASS_NAME=com.javasolver.samples.InsideOutsideProduction
rem set SOLVER=SCIP
rem set SOLVER=CLP
rem set SOLVER=GLPK
rem set SOLVER=COIN
set SOLVER=OJALGO
@echo off
cd %~dp0
call .\run
pause