import { app, BrowserWindow, Menu} from "electron";
import { ConfigGetter } from "../../services/ConfigGetter";
import { EventGetter } from "../../services/EventGetter";
import { TextGetter } from "../../services/TextGetter";
import { Dialog } from "../../services/Dialog";
import {View} from '../shared/View';
import {ProductController} from '../../controllers/ProductController';
import { isNull } from "util";
import * as path from "path";
import { EventEmitter } from "events";
const {ipcMain} = require('electron');
const { dialog } = require('electron');
// const serve = require('electron-serve');
var StaticServer = require('static-server');
const shell = require('electron').shell;

// const loadURL = serve({directory: path.join(__dirname, "./barcode_server")});

export class BarcodeScannerView extends View {

    private constructor(window: BrowserWindow, parent: BrowserWindow) {
        super(null, window, parent, 300, 300);
        // this.getWindow().webContents.openDevTools();
        this.setMenu(null);

        // Init static server
        var server = new StaticServer({
            rootPath: path.join(__dirname, "./barcode_server"),            // required, the root of the server file tree
            port: 4534,               // required, the port to listen
            name: 'barcode-server',   // optional, will set "X-Powered-by" HTTP header
            cors: '*',                // optional, defaults to undefined
            followSymlink: true,      // optional, defaults to a 404 error
            templates: {
              index: 'index.html',      // optional, defaults to 'index.html'
            //   notFound: '404.html'    // optional, defaults to undefined
            }
          });
        server.start();
    }

    private static instance: BarcodeScannerView;
    
    static getInstance(window: BrowserWindow, parent: BrowserWindow) {
        if (!BarcodeScannerView.instance) {
            BarcodeScannerView.instance = new BarcodeScannerView(window, parent);
        }
        return BarcodeScannerView.instance;
    }

    // Load view file to view window
    public show() {
        shell.openExternal("http://localhost:" + ConfigGetter.get().barcode_server.scanner_port);
    }

    // Handle all logic of this view
    logicHandle():void {

        // Handle opening request
        ipcMain.on(EventGetter.get('request_open_barcode_scanner'), () => {
            this.show();
        });

    }

}