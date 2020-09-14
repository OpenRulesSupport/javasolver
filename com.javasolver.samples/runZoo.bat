set CLASS_NAME=com.javasolver.samples.ProblemZoo
@echo off
rem ====== CONSTRAINT SOLVERS ======
rem set SOLVER=Sugar
rem set SOLVER=Constrainer
rem set SOLVER=Choco
rem set SOLVER=JSetL
rem ====== LINEAR SOLVERS ======
rem set SOLVER=Scip
set SOLVER=GLPK
rem set SOLVER=Coin
rem set SOLVER=CLP

cd %~dp0
call .\run
pause

