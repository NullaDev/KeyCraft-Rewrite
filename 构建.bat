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

rd /s /q build\libs 2>nul
rd /s /q build\tmp\reobf 2>nul
cmd /c gradlew build

move /y build\libs\*.jar .
move /y build\tmp\reobf\*.jar dev.jar
pause
