@echo off
cd %~dp0
call ..\mvnw install:install-file -Dfile=jsr331/jsr331.jar -DpomFile=jsr331/jsr331.pom.xml
call ..\mvnw install:install-file -Dfile=constrainer/jsr331.constrainer.jar -DpomFile=constrainer/jsr331.constrainer.pom.xml
call ..\mvnw install:install-file -Dfile=constrainer/constrainer.light.jar -DpomFile=constrainer/constrainer.light.pom.xml
call ..\mvnw install:install-file -Dfile=jsr331/jsr331.linear.jar -DpomFile=jsr331/jsr331.linear.pom.xml
call ..\mvnw install:install-file -Dfile=scip/jsr331.scip.jar -DpomFile=scip/jsr331.scip.pom.xml
call ..\mvnw install:install-file -Dfile=coin/jsr331.coin.jar -DpomFile=coin/jsr331.coin.pom.xml
call ..\mvnw install:install-file -Dfile=glpk/jsr331.glpk.jar -DpomFile=glpk/jsr331.glpk.pom.xml
echo.
echo JavaSolver Initialized
pause
