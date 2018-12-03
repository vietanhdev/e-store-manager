const settings = require('electron-settings');
import { ConfigGetter } from "./ConfigGetter";

export class TextGetter {
    private static instance: TextGetter;
    private languages: any;
    private currentLanguage: any;
    public data: any;
    private constructor() {

        // Load the languages
        this.languages = ConfigGetter.getInstance().config.languages;

        if (this.languages.length == 0) throw "Cannot read the language configuration";

        // If we dont have language setting, pick the first language as default
        if (!settings.has("lang"))  {
            console.log("DONT HAVE LANG SETTING!");
            this.currentLanguage = this.languages[0];
            // Save new language into app settings
            settings.set("lang", this.currentLanguage.lang);
        } else {

            // Try to find the language on the avaiable languages
            this.currentLanguage = null;
            let lang = settings.get("lang");
            for (let i = 0; i < this.languages.length; ++i) {
                if (this.languages[i].lang == lang) {
                    this.currentLanguage = this.languages[i];
                    break;
                }
            }

            // Pick the first language if we cannot find the right language
            if (this.currentLanguage == null) {
                this.currentLanguage = this.languages[0];
                // Save new language into app settings
                settings.set("lang", this.currentLanguage.lang);
            } 
        }
            
       this.data = require("./languages/" + this.currentLanguage.file);

    }
    static getInstance() {
        if (!TextGetter.instance) {
            TextGetter.instance = new TextGetter();
        }
        return TextGetter.instance;
    }
    public static get(key:string):string {
        if (typeof TextGetter.getInstance().data[key] != "undefined") {
            return TextGetter.getInstance().data[key];
        } else {
            return "";
        }
    }
    static getWithFailBack(key: string, failback: string):string {
        if (typeof TextGetter.getInstance().data[key] != "undefined") {
            return TextGetter.getInstance().data[key];
        } else {
            return failback;
        }
    }
}

