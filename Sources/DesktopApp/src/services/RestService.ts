var requestElectron = require('request');
const settings = require('electron-settings');
import { TextGetter } from "./TextGetter";
var crypto = require('crypto');

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
            // if (typeof data.password != "undefined") {
            //     data.password = crypto.createHash('md5').update(data.password).digest("hex");
            //     // console.log("Hashed Password: " + data.password);
            // }
            postData = JSON.stringify(data);
        } catch(e) {
            postData = "{}";
        }
        let headers = Object({'Content-Type': 'application/json'});
        if (auth) {
            headers['Authorization'] = "Bearer " + settings.get('account_info.token');
        }

        // console.log(this.getUrl(path));
        
        let options = {
            url: this.getUrl(path),
            method: method,
            headers: headers,
            body: postData,
            timeout: 10000
        };

        // console.log("REQUESTDATA:::::::::::::");
        // console.log(postData);

        requestElectron(options, (err: any, data: any) => {
            if (err) {
                cbFail({
                    status: 100,
                    message: TextGetter.get("connection_error")
                });
            } else {
                // console.log("DATA:::::::::::::");
                // console.log(data.body);
                // console.log(err);

                let result = {};
                try {
                    result = JSON.parse(data.body);
                } catch(e) {
                    result = {"success": false}
                    // console.log(data.body);
                }

                cbSuccess(result);
            }
            
        })

    };

}