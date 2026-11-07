set CLASS_NAME=com.javasolver.samples.DietProblem
rem ====== CONSTRAINT SOLVERS ======
rem set SOLVER=SIGAR
rem set SOLVER=CONSTRAINER
rem set SOLVER=CHOCO
rem set SOLVER=JSETL
rem ====== LINEAR SOLVERS ======
rem set SOLVER=SCIP
rem set SOLVER=GLPK
rem set SOLVER=COIN
rem set SOLVER=CLP
set SOLVER=OJALGO
@echo off
cd %~dp0
call .\run
pause