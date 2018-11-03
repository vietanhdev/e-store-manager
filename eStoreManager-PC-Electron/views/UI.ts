import { app, BrowserWindow } from "electron";
import * as path from "path";
import { UserController } from "../controllers/UserController";
import { View } from "./View";
const {LoginView} = require('./LoginView');
const {WelcomeView} = require('./WelcomeView');
const {CashierUIView} = require('./CashierUIView');
const ejse = require('ejs-electron');

export class UI {

  mainWindow: BrowserWindow;
  loadingView: View;
  viewList: Array<View>;

  public init() {

    this.viewList = new Array<View>();
    let loadingView = new View("loading", null, null);
    this.mainWindow = loadingView.getWindow();
    loadingView.getWindow().maximize();

    loadingView.show();

    // Open the DevTools.
    // this.mainWindow.webContents.openDevTools();

    // Init views
    let loginView = new LoginView(this.mainWindow, null); this.addView(loginView);
    let welcomeView = new WelcomeView(this.mainWindow, null); this.addView(welcomeView);
    let cashierUIView = new CashierUIView(this.mainWindow, null); this.addView(cashierUIView);
    

    // welcomeView.show();

    // Check user login and redirect to login page if user have not logged in
    if (!UserController.isLoggedIn()) {
      loginView.show();
    }

  }

  private addView(view: View) {
    this.viewList.push(view);
    view.eventEmitter.on('request_change_view', (viewName:string) => {
      this.changeView(viewName);
    })
  }

  private changeView(viewName:string) {
    for (let i = 0; i < this.viewList.length; i++) {
      if (this.viewList[i].view == viewName) {
        this.viewList[i].show();
      }
    }
  }

}