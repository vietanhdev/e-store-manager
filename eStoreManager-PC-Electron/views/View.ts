import { app, BrowserWindow } from "electron";
import * as path from "path";
import { EventEmitter } from "events";
const ejse = require('ejs-electron');
var events = require('events').EventEmitter;

export class View {
    
    private viewFile: string;
    private window: BrowserWindow;
    public eventEmitter: any;
    public view: string;
    

    constructor (view: string, window: BrowserWindow, parent: BrowserWindow) {

        this.view = view;
        this.eventEmitter =  new events.EventEmitter();
        
        // If the window was not passed, create another window for this view
        if (window == null) {

            // Create the browser window.
            this.window = new BrowserWindow({
                height: 800,
                width: 800,
                parent: parent
            });
            
            this.window.setMenu(null);

            // Emitted when the window is closed.
            this.window.on("closed", () => {
                // Dereference the window object, usually you would store windows
                // in an array if your app supports multi windows, this is the time
                // when you should delete the corresponding element.
                this.window = null;
            });
            
        } else {
            this.window = window;
        }

        // Calculate view file path and write to this.viewFile
        this.setViewFile(view);

        // Call logic handler
        this.logicHandle();

    }

    // Handle all logic of this view
    logicHandle():void {}

    setViewFile(view: string):void {
        this.viewFile = path.join(__dirname, "../../views/"+view+".ejs");
    }

    public getWindow():BrowserWindow {
        return this.window;
    }

    // Load view file to view window
    public show() {
        this.window.loadFile(this.viewFile); // Load view file to view window
    }

    public showLoadingModal() {
        this.window.webContents.send('loading-modal' , {status:'show'});
    }
    public hideLoadingModal() {
        this.window.webContents.send('loading-modal' , {status:'hide'});
    }


    requestChangeView(view: string) {
        this.eventEmitter.emit("request_change_view", view);
    }

}