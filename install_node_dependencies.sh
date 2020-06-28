#! /bin/bash

cd Display/displayClient

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../displayService

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../../RoomCommandManager

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../Source/TACSourceService

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../TACSource

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../MockSourceService

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../ActiveTraumaService

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'

cd ../../CommandMonitor

/c/Windows/System32/cmd.exe //c 'start cmd /c npm install'
