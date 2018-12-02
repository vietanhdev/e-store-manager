import { app } from "electron";
import { EStoreManager } from "./EStoreManager";
const settings = require('electron-settings');


app.disableHardwareAcceleration()


var eStoreManager = new EStoreManager();

// Server settings
// TODO: Remove this section
settings.set('api_config', {
  hostname: 'localhost',
  port: 8080,
  protocol: 'http'
});

// Set max listener
require('events').EventEmitter.defaultMaxListeners = Infinity;  

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on("ready", () => {
  eStoreManager.init()
});

// Quit when all windows are closed.
app.on("window-all-closed", () => {
  // On OS X it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", () => {
  // On OS X it"s common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (eStoreManager.mainWindow === null) {
    eStoreManager.init();
  }
});

// In this file you can include the rest of your app"s specific main process
// code. You can also put them in separate files and require them here.
