@echo off
if not exist main_kcrw (
::��ǰ����kcrw
echo ����kcrw
copy /y kcrw_build.gradle ..\build.gradle
) else if not exist main_nullacore (
::��ǰ����nullacore
echo ����nullacore
copy /y nullacore_build.gradle ..\build.gradle
)
cd ..
cmd /c gradlew build
move /y build\libs\*.jar .
pause
