import { app, BrowserWindow, Menu, MenuItem } from "electron";
import * as path from "path";
import {View} from './views/shared/View';
import {UserController} from './controllers/UserController';
import {TextGetter} from './services/TextGetter';

const ejse = require('ejs-electron');
const settings = require('electron-settings');

// Load the language file
var textGetter = TextGetter.getInstance();
ejse.data('gettext', textGetter.data);

// Import views
const {LoginView} = require('./views/login/LoginView');
const {WelcomeView} = require('./views/welcome/WelcomeView');
const {CashierView} = require('./views/cashier/CashierView');
const {AboutView} = require('./views/about/AboutView');
const {PreferenceView} = require('./views/preferences/PreferenceView');

const {EmployeeView} = require('./views/employees/EmployeeView');
const {AddEmployeeView} = require('./views/employees/AddEmployeeView');

const {CustomerView} = require('./views/customers/CustomerView');
const {AddCustomerView} = require('./views/customers/AddCustomerView');
const {EditCustomerView} = require('./views/customers/EditCustomerView');

const {SupplierView} = require('./views/suppliers/SupplierView');
const {AddSupplierView} = require('./views/suppliers/AddSupplierView');
const {EditSupplierView} = require('./views/suppliers/EditSupplierView');

const {ProductTypeView} = require('./views/product_types/ProductTypeView');
const {AddProductTypeView} = require('./views/product_types/AddProductTypeView');
const {EditProductTypeView} = require('./views/product_types/EditProductTypeView');

const {PasswordInputView} = require('./views/password_input/PasswordInputView');


export class EStoreManager {

  mainWindow: BrowserWindow;
  loadingView: View;
  viewList: Array<any>;
  menu:Menu;

  EStoreManager() {}

  public init() {

    let userController = new UserController();

    // Create the main menu of the application
    this.menu = new Menu();
    Menu.setApplicationMenu(this.menu);

    this.viewList = new Array<View>();
    let loadingView = new View("loading", null, null);
    this.mainWindow = loadingView.getWindow();
    loadingView.getWindow().maximize();

    // Open the DevTools.
    this.mainWindow.webContents.openDevTools();

    // Init views
    let loginView = LoginView.getInstance(this.mainWindow, null); this.addView(loginView);
    let welcomeView = WelcomeView.getInstance(this.mainWindow, null); this.addView(welcomeView);
    let cashierView = CashierView.getInstance(this.mainWindow, null); this.addView(cashierView);
    let employeeView = EmployeeView.getInstance(this.mainWindow, null); this.addView(employeeView);
    let addEmployeeView = AddEmployeeView.getInstance(null, null); this.addView(addEmployeeView);
    let aboutView = AboutView.getInstance(null, this.mainWindow); this.addView(aboutView);
    
    let customerView = CustomerView.getInstance(this.mainWindow, null); this.addView(customerView);
    let addCustomerView = AddCustomerView.getInstance(null, null); this.addView(addCustomerView);
    let editCustomerView = EditCustomerView.getInstance(null, null); this.addView(editCustomerView);
    
    let supplierView = SupplierView.getInstance(this.mainWindow, null); this.addView(supplierView);
    let addSupplierView = AddSupplierView.getInstance(null, null); this.addView(addSupplierView);
    let editSupplierView = EditSupplierView.getInstance(null, null); this.addView(editSupplierView);

    let productTypeView = ProductTypeView.getInstance(this.mainWindow, null); this.addView(productTypeView);
    let addProductTypeView = AddProductTypeView.getInstance(null, null); this.addView(addProductTypeView);
    let editProductTypeView = EditProductTypeView.getInstance(null, null); this.addView(editProductTypeView);

    // PasswordInputView is shared between views to input password
    // This view MUST BE initialize on boot
    let passwordInputView = PasswordInputView.getInstance(null, null); this.addView(passwordInputView);
    passwordInputView.hide();

    // Init Menu actions
    this.menu.append(new MenuItem({
      label: TextGetter.get("welcome_screen"),
          click: () => {
            this.changeView('welcome');
          }
    }));
    this.menu.append(new MenuItem({label: TextGetter.get("preferences"),
    click: () => {
      let preferenceView = new PreferenceView(null, this.mainWindow);
      preferenceView.show();
    }
    }));
    this.menu.append(new MenuItem({role: 'help',
    submenu: [
      {
          label: TextGetter.get("about_us"),
          click: () => {
            aboutView.show();
          }
      }
      ]
    }));
    this.menu.append(new MenuItem({label: TextGetter.get("logout"),
      click: () => {
        userController.logout();
        this.changeView('login');
      }
    }));

    this.mainWindow.setMenu(this.menu);

    // welcomeView.show();
    loadingView.show();

    // Check user login and redirect to login page if user have not logged in
    userController.isLoggedIn(() => {
      welcomeView.show();
    }, () => {
      loginView.show();
    });

  }

  private addView(view: any) {
    this.viewList.push(view);
    view.getEventEmitter().on('request_change_view', (viewName:string) => {
      this.changeView(viewName);
    })
  }

  private changeView(viewName:string) {
    switch(viewName) {
      case "preferences": 
        let preferenceView = PreferenceView.getInstance(null, this.mainWindow);
        preferenceView.show();
        break;
      default:
        for (let i = 0; i < this.viewList.length; i++) {
          // console.log(this.viewList[i].getInstance(this.mainWindow, null).getView());
          if (this.viewList[i].getInstance(this.mainWindow, null).getView() == viewName) {
            this.viewList[i].getInstance(this.mainWindow, null).show();
            break;
          }
        }
    }
  }

}