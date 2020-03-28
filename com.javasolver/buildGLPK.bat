@echo off
cd %~dp0
call mvnw -f pomGLPK.xml install
pause