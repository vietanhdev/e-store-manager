const { net } = require('electron')
const settings = require('electron-settings');

export class RestService {

    private hostname:string;
    private protocol:string;
    private port:number;

    constructor(hostname:string="localhost", protocol:string="http", port:number=8080) {
        this.hostname = hostname;
        this.protocol = protocol;
        this.port = port;
    }

    request(method:string, path: string, data: any, cbSuccess: (any), cbFail: (any), auth: boolean):void {

        let postData = JSON.stringify(data);
        // let headers = Object({'Content-Type': 'application/json'});
        // if (typeof header != "undefined") {
        //     for (var key in header) {
        //         if (header.hasOwnProperty(key)) {
        //             headers[key] = header[key];
        //         }
        //     }
        // }
        let headers = Object({'Content-Type': 'application/json'});
        if (auth) {
            headers['Authorization'] = "Bearer " + settings.get('account_info.token');
        }
        
        let options = {
            hostname: this.hostname,
            port: this.port,
            path: path,
            method: method,
            headers: headers,
            body: postData
        };

        const request = net.request(options);
        request.write(postData);
        
        let returnData: string = '';
        
        request.on('response', (response) => {
            console.log(`STATUS: ${response.statusCode}`)
            console.log(`HEADERS: ${JSON.stringify(response.headers)}`)
            response.on('data', (chunk) => {
                returnData += chunk;
            })
            response.on('end', () => {
                cbSuccess(JSON.parse(returnData));
            })
        })
        request.on('error', (e:any) => {
            cbFail({
                success: false,
                errorCode: 100,
                message: "Connection error!",
            });
        });
        request.end()

    };

}