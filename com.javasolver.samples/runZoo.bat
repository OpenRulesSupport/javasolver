set CLASS_NAME=com.javasolver.samples.ProblemZoo
rem ====== CONSTRAINT SOLVERS ======
set SOLVER=Sugar
rem set SOLVER=Constrainer
rem set SOLVER=Choco
rem set SOLVER=JSetL
rem ====== LINEAR SOLVERS ======
rem set SOLVER=Scip
rem set SOLVER=GLPK
rem set SOLVER=Coin

@echo off
cd %~dp0
call .\run
pause