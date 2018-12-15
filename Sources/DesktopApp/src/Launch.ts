import { app } from "electron";
import { EStoreManager } from "./EStoreManager";
import { ConfigGetter } from "./services/ConfigGetter";
var settings:any;

// Try to import settings
try {
  settings = require('electron-settings');
} catch(e) {
  // do nothing
} finally {
  settings = require('electron-settings');
}

app.disableHardwareAcceleration()


var eStoreManager = new EStoreManager();

// Default Server Settings
let config = ConfigGetter.get();
if (!settings.has("initialized")) {
  settings.set('api_config', {
    hostname: config.default_settings.hostname,
    port: config.default_settings.server_port,
    protocol: config.default_settings.server_protocol
  });
  settings.set("lang", config.default_settings.lang);
  settings.set("initialized", true);
}

// Init the value of verified logged in
settings.set('verified_logged_in', false);

// Set max listener
require('events').EventEmitter.defaultMaxListeners = 0;  
process.setMaxListeners(0);
// EventEmitter.defaultMaxListeners = 0;

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