var requestElectron = require('request');
const settings = require('electron-settings');
import { TextGetter } from "./TextGetter";

export class RestService {

    private hostname:string;
    private protocol:string;
    private port:number;

    constructor(hostname:string="localhost", protocol:string="http", port:number=8080) {
        this.hostname = hostname;
        this.protocol = protocol;
        this.port = port;
    }


    public getUrl(path:string):string {
        return this.protocol + "://" + this.hostname + ":" + this.port + path;
    }


    request(method:string, path: string, data: any, cbSuccess: (any), cbFail: (any), auth: boolean):void {

        let postData:string;
        try {
            postData = JSON.stringify(data);
        } catch(e) {
            postData = "{}";
        }
        let headers = Object({'Content-Type': 'application/json'});
        if (auth) {
            headers['Authorization'] = "Bearer " + settings.get('account_info.token');
        }
        
        let options = {
            url: this.getUrl(path),
            method: method,
            headers: headers,
            body: postData,
            timeout: 10000
        };

        console.log("REQUESTDATA:::::::::::::");
        console.log(postData);

        requestElectron(options, (err: any, data: any) => {
            console.log("DATA:::::::::::::");
            console.log(data);
            if (err) {
                cbFail({
                    status: 100,
                    message: TextGetter.get("unknown_error")
                });
            } else {
                
                cbSuccess(JSON.parse(data.body));
            }
            
        })

    };

}