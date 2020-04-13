#! /bin/bash

cd SourceSample

/c/Windows/System32/cmd.exe //c 'start cmd /c launcher.sh'

cd ../DisplaySample

/c/Windows/System32/cmd.exe //c 'start cmd /c launcher.sh'

cd display-client

/c/Windows/System32/cmd.exe //c 'start cmd /c npm start'

cd ../../

cd ASTRARoomManager/out/artifacts/ASTRARoomManager_jar 

/c/Windows/System32/cmd.exe //c 'start cmd /c java -jar ASTRARoomManager.jar'