import { app, BrowserWindow, Menu, MenuItem } from "electron";
import * as path from "path";
import {View} from './views/shared/View';
import {UserController} from './controllers/UserController';
import {TextGetter} from './services/TextGetter';
import { Dialog } from "./services/Dialog";
const { dialog } = require('electron');

const ejse = require('ejs-electron');
const settings = require('electron-settings');
const {ipcMain} = require('electron');

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

const {InvoiceView} = require('./views/invoices/InvoiceView');
const {AddInvoiceView} = require('./views/invoices/AddInvoiceView');
const {EditInvoiceView} = require('./views/invoices/EditInvoiceView');

const {ProductView} = require('./views/products/ProductView');
const {AddProductView} = require('./views/products/AddProductView');
const {EditProductView} = require('./views/products/EditProductView');

const {ImportProductView} = require('./views/import_products/ImportProductView');
const {AddImportItemView} = require('./views/import_products/AddImportItemView');
const {AddImportBillView} = require('./views/import_products/AddImportBillView');
const {ViewImportBillView} = require('./views/import_products/ViewImportBillView');

const {SellBillView} = require('./views/sell_bills/SellBillView');
const {ViewSellBillView} = require('./views/sell_bills/ViewSellBillView');

const {ReportView} = require('./views/reports/ReportView');

const {BarcodeScannerView} = require('./views/barcode_scanner/BarcodeScannerView');

const {PasswordInputView} = require('./views/password_input/PasswordInputView');


export class EStoreManager {

  mainWindow: BrowserWindow;
  viewList: Array<any>;
  menu:Menu;
  userController:UserController;

  EStoreManager() {}

  getMainWindow() {
    return this.mainWindow;
  }

  public init() {

    this.userController = new UserController();

    // Create the main menu of the application
    this.menu = new Menu();
    Menu.setApplicationMenu(this.menu);

    this.viewList = new Array<View>();
  
    this.mainWindow = new BrowserWindow();
    this.mainWindow.maximize();

    // Open the DevTools.
    // this.mainWindow.webContents.openDevTools();

    // Handle request_change_view event from browser thread
    ipcMain.on('request_change_view', (event:any, data:any) => {
        this.changeView(data);
    });

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

    let invoiceView = InvoiceView.getInstance(this.mainWindow, null); this.addView(invoiceView);
    let addInvoiceView = AddInvoiceView.getInstance(null, null); this.addView(addInvoiceView);
    let editInvoiceView = EditInvoiceView.getInstance(null, null); this.addView(editInvoiceView);

    let productView = ProductView.getInstance(this.mainWindow, null); this.addView(productView);
    let addProductView = AddProductView.getInstance(null, null); this.addView(addProductView);
    let editProductView = EditProductView.getInstance(null, null); this.addView(editProductView);

    let importProductView = ImportProductView.getInstance(this.mainWindow, null); this.addView(importProductView);
    let addImportItemView = AddImportItemView.getInstance(null, null); this.addView(addImportItemView);
    let addImportBillView = AddImportBillView.getInstance(null, null); this.addView(addImportBillView);
    let viewImportBillView = ViewImportBillView.getInstance(null, null); this.addView(viewImportBillView);

    let sellBillView = SellBillView.getInstance(this.mainWindow, null); this.addView(sellBillView);
    let viewSellBillView = ViewSellBillView.getInstance(null, null); this.addView(viewSellBillView);

    let reportView = ReportView.getInstance(this.mainWindow, null); this.addView(reportView);

    let barcodeScannerView = BarcodeScannerView.getInstance(null, null); this.addView(barcodeScannerView);
    // barcodeScannerView.show();
    

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
        this.userController.logout();
        this.changeView('login');
      }
    }));

    this.mainWindow.setMenu(this.menu);

    // Check user login and redirect to login page if user have not logged in
    this.userController.isLoggedIn(() => {
      welcomeView.show();
      settings.set('verified_logged_in', true);
    }, () => {
      loginView.show();
      settings.set('verified_logged_in', false);
    });

  }

  private addView(view: any) {
    this.viewList.push(view);
    view.getEventEmitter().on('request_change_view', (viewName:string) => {
      this.changeView(viewName);
    })
  }


  // Change from one view to another view
  private changeView(viewName:string) {
    switch(viewName) {
      case "preferences": 
        let preferenceView = PreferenceView.getInstance(null, this.mainWindow);
        preferenceView.show();
        break;
      default:

        // Find the view in the view list
        for (let i = 0; i < this.viewList.length; i++) {
          
          // If found the view
          if (this.viewList[i].getInstance(this.mainWindow, null).getView() == viewName) {

            // If view require permission to access
            if ( this.viewList[i].roles != undefined) {
                let havePermission = false;
                let roles = settings.get("account_info.roles");

                // Check the permission of the user
                if ( this.viewList[i].roles.includes(roles)) havePermission = true;
                if (!havePermission) {
                  dialog.showMessageBox({
                      message: TextGetter.get("you_dont_have_permission"),
                      buttons: ["OK"]
                  });
                } else {
                  this.viewList[i].show();
                }
            } else {
                this.viewList[i].show();
            }
            
            
          }
        }
    }
  }

}