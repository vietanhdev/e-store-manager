import { app, BrowserWindow } from "electron";
import * as path from "path";
import { UserController } from "../controllers/UserController";
import { View } from "./View";
const {LoginView} = require('./LoginView');


export class UI {

  mainView: View;

  public init() {

    let mainView = new View("loading", null, null);
    mainView.getWindow().maximize();

    // Open the DevTools.
    // mainView.getWindow().webContents.openDevTools();

    // Check user login and redirect to login page if user have not logged in
    if (!UserController.isLoggedIn()) {
      let loginView = new LoginView(mainView.getWindow(), null);
    }

  }

  getMainView(): View {
    return this.mainView;
  }

}