import { app, BrowserWindow, Menu, MenuItem } from "electron";
import * as path from "path";
import {View} from './Views/View/View';
import {UserController} from './Controllers/Users/UserController';


const ejse = require('ejs-electron');
const settings = require('electron-settings');

// Import views
// import {LoginView} from './Views/LoginView/LoginView';
const {LoginView} = require('./Views/LoginView/LoginView');
const {WelcomeView} = require('./Views/WelcomeView/WelcomeView');
const {CashierView} = require('./Views/CashierView/CashierView');
const {AboutView} = require('./Views/AboutView/AboutView');
const {PreferenceView} = require('./Views/PreferenceView/PreferenceView');


// const {Menu} = require('electron');


export class UI {

  mainWindow: BrowserWindow;
  loadingView: View;
  viewList: Array<View>;
  menu:Menu;

  UI() {}

  public init() {

    // Load the main menu of the application
    const mainMenuTemplate = require('./Views/View/MainMenuTemplate').template;
    this.menu = Menu.buildFromTemplate(mainMenuTemplate);
    Menu.setApplicationMenu(this.menu);

    this.viewList = new Array<View>();
    let loadingView = new View("LoadingView/loading", null, null);
    this.mainWindow = loadingView.getWindow();
    loadingView.getWindow().maximize();

    // Open the DevTools.
    this.mainWindow.webContents.openDevTools();

    // Init views
    let loginView = new LoginView(this.mainWindow, null); this.addView(loginView);
    let welcomeView = new WelcomeView(this.mainWindow, null); this.addView(welcomeView);
    // let cashierView = new CashierView(this.mainWindow, null); this.addView(CashierView);
    

    // Init Menu actions
    this.menu.append(new MenuItem({label: 'Goto',
    submenu: [
      {
          label: 'Welcome Screen',
          click: () => {
            this.changeView('welcome');
          }
      }
      ]
    }));
    this.menu.append(new MenuItem({label: 'Preferences',
    click: () => {
      let preferenceView = new PreferenceView(null, this.mainWindow);
      preferenceView.show();
    }
    }));
    this.menu.append(new MenuItem({role: 'help',
    submenu: [
      {
          label: 'About us',
          click: () => {
            let aboutView = new AboutView(null, this.mainWindow);
            aboutView.show();
          }
      }
      ]
    }));

    this.mainWindow.setMenu(this.menu);

    // welcomeView.show();
    loadingView.show();

    // Check user login and redirect to login page if user have not logged in
    if (!UserController.isLoggedIn()) {
      loginView.show();
    }

  }

  private addView(view: View) {
    this.viewList.push(view);
    view.getEventEmitter().on('request_change_view', (viewName:string) => {
      this.changeView(viewName);
    })
  }

  private changeView(viewName:string) {
    for (let i = 0; i < this.viewList.length; i++) {
      if (this.viewList[i].getView() == viewName) {
        this.viewList[i].show();
      }
    }
  }

}