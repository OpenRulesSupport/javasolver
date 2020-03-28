@echo off
cd %~dp0
call mvnw -f pomConstrainer.xml install
pause