@echo off
if not exist main_kcrw (
::当前开发kcrw
ren main main_kcrw
ren main_nullacore main
) else if not exist main_nullacore (
::当前开发nullacore
ren main main_nullacore
ren main_kcrw main
)
