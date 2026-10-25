set CLASS_NAME=com.javasolver.samples.InsideOutsideProduction
rem set SOLVER=Scip
rem set SOLVER=CLP
rem set SOLVER=GLPK
rem set SOLVER=Coin
set SOLVER=ojAlgo
@echo off
cd %~dp0
call .\run
pause