set CLASS_NAME=com.javasolver.samples.XYZ
@echo off
rem ====== CONSTRAINT SOLVERS ======
rem set SOLVER=CONSTRAINER
set SOLVER=MAXICP
set SOLVER=CHOCO
rem set SOLVER=JSETL
set SOLVER=JACOP

cd %~dp0
call .\run
pause

