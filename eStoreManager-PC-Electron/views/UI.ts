import { app, BrowserWindow } from "electron";
import * as path from "path";
import { UserController } from "../controllers/UserController";

export class UI {

    // Main window of the application
    public mainWindow: Electron.BrowserWindow;

    public initMainWindow() {
      // Create the browser window.
      this.mainWindow = new BrowserWindow({
        height: 600,
        width: 800,
      });
    
      this.mainWindow.setMenu(null);
      this.mainWindow.maximize();

      this.mainWindow.loadFile(path.join(__dirname, "../../views/loading.html"));

      // Open the DevTools.
      this.mainWindow.webContents.openDevTools();

      // Check user login and redirect to login page if user have not logged in
      if (!UserController.isLoggedIn()) {
        this.mainWindow.loadFile(path.join(__dirname, "../../views/login.html"));
        require("../views/LoginView");
      }
      
    
      // Emitted when the window is closed.
      this.mainWindow.on("closed", () => {
        // Dereference the window object, usually you would store windows
        // in an array if your app supports multi windows, this is the time
        // when you should delete the corresponding element.
        this.mainWindow = null;
      });
    }

}