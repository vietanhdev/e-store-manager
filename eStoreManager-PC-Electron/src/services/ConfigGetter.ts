export class ConfigGetter {
    private static instance: ConfigGetter;
    public config: any;
    private constructor() {
        
    }
    static getInstance() {
        if (!ConfigGetter.instance) {
            ConfigGetter.instance = new ConfigGetter();
            ConfigGetter.instance.config = require("./config.json");
        }
        return ConfigGetter.instance;
    }
    static getConfig() {
        return ConfigGetter.getInstance().config;
    }
}

