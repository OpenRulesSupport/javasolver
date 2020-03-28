@echo off
cd %~dp0
call mvnw -f pomScip.xml clean install
pause