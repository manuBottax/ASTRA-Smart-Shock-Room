#! /bin/bash

cd ../DisplaySample

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install & launcher.sh'

cd display-client

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install && npm start'

cd ../../

cd ASTRARoomManager/out/artifacts/ASTRARoomManager_jar 

/c/Windows/System32/cmd.exe //c 'start cmd /c java -jar ASTRARoomManager.jar'