@echo off
if not exist main_kcrw (
::当前开发kcrw
echo 构建kcrw
copy /y kcrw_build.gradle ..\build.gradle
) else if not exist main_nullacore (
::当前开发nullacore
echo 构建nullacore
copy /y nullacore_build.gradle ..\build.gradle
)
cd ..
cmd /c gradlew build
move /y build\libs\*.jar .
pause
