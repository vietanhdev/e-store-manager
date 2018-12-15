import * as path from "path";
import { EventEmitter } from "events";
import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import {UserController} from '../../controllers/UserController';
import { isNull } from "util";
import { Dialog } from "../../services/Dialog";
import { TextGetter } from "../../services/TextGetter";
const {ipcMain} = require('electron');
const { dialog } = require('electron');
const settings = require('electron-settings');


export class View {
    
    private viewName: string;
    private viewFile: string;
    private window: BrowserWindow;
    private eventEmitter: EventEmitter;
    private originHeight: number;
    private originWidth: number;
    private originWindow: BrowserWindow;
    private originParent: BrowserWindow;
    private roles: string;
    private menu:Menu = null;

    constructor (viewName: string, window: BrowserWindow, parent: BrowserWindow, width: number = 800, height: number = 600) {

        // Save all window settings
        this.viewName = viewName;
        this.eventEmitter =  new EventEmitter();
        this.originHeight = height;
        this.originWidth = width;
        this.originWindow = window;
        this.originParent = parent;

        // Create a window
        // this.createWindow();

        // Calculate view file path and write to this.viewFile
        if (ConfigGetter.get().view[viewName]) {
            this.viewFile = path.join(__dirname, "../"+ConfigGetter.get().view[viewName].path);
            this.roles = ConfigGetter.get().view[viewName].roles;
        }

        // Call logic handler
        this.logicHandle();

    }

    createWindow():void {
        
        if (!isNull(this.originWindow)) {
            this.window = this.originWindow;
        } else if (this.window == null) {

            // Create the browser window.
            this.window = new BrowserWindow({
                height: this.originHeight,
                width: this.originWidth,
                parent: this.originParent,
                webPreferences: {
                    nodeIntegrationInWorker: true
                }
            });

            // Emitted when the window is closed.
            this.window.on("closed", (event: any) => {
                // Dereference the window object, usually you would store windows
                // in an array if your app supports multi windows, this is the time
                // when you should delete the corresponding element.
                this.window = null;
            });

            // Set Menu
            this.window.setMenu(this.menu);
            
        }

    }

    getInstance() {
        return this;
    }

    // Handle all logic of this view
    logicHandle():void {}

    getView():string {
        return this.viewName;
    }

    getEventEmitter():EventEmitter {
        return this.eventEmitter;
    }

    getViewFile(): string {
        return this.viewFile;
    }

    setMenu(menu: Menu): void {
        this.menu = menu;
    }

    public setOriginWindow(window: BrowserWindow) {
        this.originWindow = window;
    }

    public setOriginParent(parent: BrowserWindow) {
        this.originParent = parent;
    }

    public getWindow():BrowserWindow {
        this.createWindow();
        return this.window;
    }

    public getParent():BrowserWindow {
        return this.originParent;
    }

    // Load view file to view window
    public show() {

        // console.log("typeof this.roles" + typeof this.roles);
        if (this.roles != undefined) {

            let havePermission = false;
            let roles = settings.get("account_info.roles");
            if (this.roles.includes(roles)) havePermission = true;

            if (!havePermission) {
                Dialog.showDialog("error", null, TextGetter.get("you_dont_have_permission"), this.getWindow(), () => {});
            } else {
                this.getWindow().loadFile(this.viewFile); // Load view file to view window
                this.getWindow().show();
            }
        } else {
            this.getWindow().loadFile(this.viewFile); // Load view file to view window
            this.getWindow().show();
        }
        
    }

    
    public hide() {
        try {
            if (this.window != null) {
                this.window.close();
            }
        } catch (e) {}
    }


    public isVisible():boolean {
        if (this.window == null) return false;
        else return true;
    }


    requestChangeView(view: string) {
        this.eventEmitter.emit("request_change_view", view);
    }

}