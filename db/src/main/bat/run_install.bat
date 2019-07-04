@echo off
@chcp 65001

:choice
set /P c=При пересоздании БД будет удалена текущая версия БД, если она есть. Продолжить [Y/N]?
if /I "%c%" equ "Y" goto :run_install
if /I "%c%" equ "N" goto :exit
goto :choice

:run_install

set SU_DB_NAME=postgres
set SU_USER=postgres
set SU_PASSWORD=postgres

psql -U %SU_USER% -f ../sql/install.sql^
    -v SU_DB_NAME="%SU_DB_NAME%"^
    -v SU_USER="%SU_USER%"^
    -v SU_PASSWORD="%SU_PASSWORD%"

:exit