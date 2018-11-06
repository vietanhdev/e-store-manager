var http = require('http');
var https = require('https');
var querystring = require('querystring');


export class RestService {

    private hostname:string;
    private protocol:string;
    private port:number;

    constructor(hostname:string="localhost", protocol:string="http", port:number=80) {
        this.hostname = hostname;
        this.protocol = protocol;
        this.port = port;
    }

    request(method:string, path: string, data: any, cbSuccess: (any), cbFail: (any)):void {

        let postData = querystring.stringify(data);
        
        
        let options = {
            hostname: this.hostname,
            port: this.port,
            path: path,
            method: method,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Length': Buffer.byteLength(postData)
            }
        };
        
        let returnData: string = '';

        let httpPackage;
        if (this.protocol === 'https') {
            httpPackage = https;
        } else {
            httpPackage = http;
        }
        
        let req = httpPackage.request(options, (res:any) => {
            // console.log(`STATUS: ${res.statusCode}`);
            // console.log(`HEADERS: ${JSON.stringify(res.headers)}`);
            res.setEncoding('utf8');
            res.on('data', (chunk:any) => {
                returnData += chunk;
            });
            res.on('end', () => {
                cbSuccess(JSON.parse(returnData));
            })
        });
        
        req.on('error', (e:any) => {
            cbFail({
                success: false,
                errorCode: 100,
                message: "Connection error!",
            });
        });
        
        // write data to request body
        req.write(postData);
        req.end();

    };

}