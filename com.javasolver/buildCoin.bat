@echo off
cd %~dp0
call mvnw -f pomCoin.xml install
pause