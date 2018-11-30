import {BrowserWindow} from "electron";
import { TextGetter } from "./TextGetter";
import { isUndefined } from "util";
const { dialog } = require('electron');

export class Dialog {
    private static instance: Dialog;
    public config: any;
    private constructor() {
        
    }
    static getInstance() {
        if (!Dialog.instance) {
            Dialog.instance = new Dialog();
            Dialog.instance.config = require("./data/config.json");
        }
        return Dialog.instance;
    }
    static showDialog(type: string, message_code: string, message_content:string, window: BrowserWindow):void {
        let title:string = "";
        switch (type) {
            case "error": title = "Error: "; break;
            default: title = "Info";
        }

        let message: string;
        if (message_content) {
            message = message_content;
        } else {
            message = TextGetter.get(message_code);
            if (message == "") {
                message = message_code;
            }
        }

        dialog.showMessageBox(window, Object({
            type: type,
            title: title,
            message: message,
            buttons: ["OK"]
        }));

    }

    static showDialogFromRespond(type: string, respond: any, window: BrowserWindow):void {
        let title:string = "";
        switch (type) {
            case "error": title = TextGetter.get("error_2dot"); break;
            default: title = TextGetter.get("info");
        }

        let message:string = "";
        if (isUndefined(respond.code)) {
            message = TextGetter.getWithFailBack(respond.code, respond.message);
        } else if (isUndefined(respond.error)) {
            message = TextGetter.getWithFailBack(respond.error, respond.message);
        } else if (isUndefined(respond.error)) {
            message = TextGetter.get("unknown_error");
        }

        dialog.showMessageBox(window, Object({
            type: type,
            title: title,
            message: message,
            buttons: ["OK"]
        }));

    }
}

