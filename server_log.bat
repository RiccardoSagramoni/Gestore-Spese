@echo off
set L=c:\prg\libs\
set LIBS=%L%xstream-1.4.7.jar;%L%xmlpull-1.1.3.1.jar;%L%xpp3_min-1.1.4c.jar;
set CLASSES=.\build\classes\

c:\prg\jdk8\bin\java -classpath %LIBS%%CLASSES% ServerLogEventiGUI
pause
