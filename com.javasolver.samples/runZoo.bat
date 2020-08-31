set CLASS_NAME=com.javasolver.samples.ProblemZoo
@echo off
rem ====== CONSTRAINT SOLVERS ======
rem set SOLVER=Sugar
rem set SOLVER=Constrainer
rem set SOLVER=Choco
set SOLVER=JSetL
rem ====== LINEAR SOLVERS ======
rem set SOLVER=Scip
rem set SOLVER=GLPK
rem set SOLVER=Coin

cd %~dp0
call .\run
pause

