var http = require('http');
var https = require('https');
var querystring = require('querystring');
const settings = require('electron-settings');

export class RestService {

    request(method:string, path: string, data: any, cbSuccess: (any), cbFail: (any)) {

        let postData = querystring.stringify(data);
        let hostname:string = settings.get('api_config.hostname');
        let port:number = settings.get('api_config.port');
        let protocol: string = settings.get('api_config.protocol');
        
        let options = {
            hostname: hostname,
            port: port,
            path: path,
            method: method,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Length': Buffer.byteLength(postData)
            }
        };
        
        let returnData: string = '';

        let httpPackage;
        if (protocol === 'https') {
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