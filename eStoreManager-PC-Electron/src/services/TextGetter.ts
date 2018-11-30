export class TextGetter {
    private static instance: TextGetter;
    public data: any;
    private constructor() {
        
    }
    static getInstance() {
        if (!TextGetter.instance) {
            TextGetter.instance = new TextGetter();
            TextGetter.instance.data = require("./languages/en.json");
        }
        return TextGetter.instance;
    }
    static get(key:string):string {
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

