@echo off
if not exist main_kcrw (
::��ǰ����kcrw
ren main main_kcrw
ren main_nullacore main
) else if not exist main_nullacore (
::��ǰ����nullacore
ren main main_nullacore
ren main_kcrw main
)
