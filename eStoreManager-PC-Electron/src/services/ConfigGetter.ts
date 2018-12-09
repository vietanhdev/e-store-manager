export class ConfigGetter {
    private static instance: ConfigGetter;
    public config: any = null;
    private constructor() {
        
    }
    static getInstance() {
        if (!ConfigGetter.instance) {
            ConfigGetter.instance = new ConfigGetter();
            ConfigGetter.instance.config = require("./data/config.json");
        }
        return ConfigGetter.instance;
    }
    static get() {
        return ConfigGetter.getInstance().config;
    }
}

