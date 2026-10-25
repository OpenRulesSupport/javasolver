set CLASS_NAME=com.javasolver.samples.XYZ
@echo off
rem ====== CONSTRAINT SOLVERS ======
rem set SOLVER=Sugar
rem set SOLVER=Constrainer
rem set SOLVER=Choco
rem set SOLVER=MaxiCP
rem set SOLVER=JSetL
set SOLVER=JaCoP

cd %~dp0
call .\run
pause

