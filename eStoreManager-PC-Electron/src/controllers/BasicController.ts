import {RestService} from '../services/RestService';
import { stringify } from 'querystring';
const settings = require('electron-settings');

export class Controller {

    private rest: RestService;

    constructor() {
        let hostname:string = settings.get('api_config.hostname');
        let port:number = settings.get('api_config.port');
        let protocol: string = settings.get('api_config.protocol');
        this.rest = new RestService(hostname, protocol, port);
    }

    public getRestService() {
        return this.rest;
    }

}