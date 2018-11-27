const { net } = require('electron')


export class RestService {

    private hostname:string;
    private protocol:string;
    private port:number;

    constructor(hostname:string="localhost", protocol:string="http", port:number=8080) {
        this.hostname = hostname;
        this.protocol = protocol;
        this.port = port;
    }

    request(method:string, path: string, data: any, cbSuccess: (any), cbFail: (any)):void {

        let postData = JSON.stringify(data);
        
        let options = {
            hostname: this.hostname,
            port: this.port,
            path: path,
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: postData
        };

        console.log(options)

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

        console.log(postData);

    };

}