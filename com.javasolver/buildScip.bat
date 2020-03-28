@echo off
cd %~dp0
call mvnw -f pomScip.xml install 
pause