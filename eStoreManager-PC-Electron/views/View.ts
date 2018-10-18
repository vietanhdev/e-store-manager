import { app, BrowserWindow } from "electron";
import * as path from "path";

class View {
    
    private viewFile: string;
    private window: BrowserWindow;

    constructor (view: string, window: any) {
        
        // If the window was not passed, create another window for this view
        if (window == null) {

        }


    }

    setViewFile(view: string) {
        viewFile = path.join(__dirname, "../../views/"+view+".html");
    }


}