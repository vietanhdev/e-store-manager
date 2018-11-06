const settings = require('electron-settings');

class Settings {

    public static getAPIHostname():string {
        return settings.get('api_config.hostname');
    }
    public static setAPIHostname(hostname:string):void {
        settings.set('api_config.hostname', hostname);
    }
    public static getAPIPort():number {
        return settings.get('api_config.port');
    }
    public static setAPIPort(port:number):void {
        settings.set('api_config.port', port);
    }
    public static getAPIProtocol():string {
        return settings.get('api_config.protocol');
    }
    public static setAPIProtocol(protocol:string):void {
        settings.set('api_config.protocol', protocol);
    }
    public static getStoreAddress():string {
        return settings.get('store_info.address');
    }
    public static setStoreAddress(address:string):void {
        settings.set('store_info.address', address);
    }
    public static getStoreName():string {
        return settings.get('store_info.name');
    }
    public static setStoreName(name:string):void {
        settings.set('store_info.name', name);
    }
    public static getStartOnBoot():boolean {
        return settings.get('app_settings.startonboot');
    }
    public static setStartOnBoot(startonboot:boolean):void {
        settings.set('app_settings.startonboot', startonboot);
    }

}