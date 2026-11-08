set CLASS_NAME=com.javasolver.samples.ProblemZoo
@echo off
rem ====== CONSTRAINT SOLVERS ======
rem set SOLVER=SUGAR
rem set SOLVER=CONSTRAINER
rem set SOLVER=CHOCO
rem set SOLVER=JSETL
rem ====== LINEAR SOLVERS ======
rem set SOLVER=SCIP
rem set SOLVER=GLPK
rem set SOLVER=COIN
set SOLVER=CLP
set SOLVER=OJALGO
cd %~dp0
call .\run
pause

