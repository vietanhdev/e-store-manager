# eStore Manager - Desktop application


## Run
- Install nodejs first.

~~~
npm i
npm start
~~~


## Build (X64)

~~~
cd ..
electron-packager ./DesktopApp/ eStoreManager --overwrite --asar=true --platform=linux --arch=x64 --icon=assets/logo.png --prune=true --out=DesktopApp-build
~~~
