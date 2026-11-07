set CLASS_NAME=com.javasolver.samples.XYZreal
@echo off

rem ====== LINEAR SOLVERS ======
rem set SOLVER=SCIP
rem set SOLVER=GLPK
rem set SOLVER=COIN
rem set SOLVER=CLP

cd %~dp0
call .\run
pause

