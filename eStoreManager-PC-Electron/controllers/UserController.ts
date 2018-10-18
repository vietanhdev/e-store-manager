import {RestService} from '../services/RestService';
import { stringify } from 'querystring';

export class UserController {

    private token: string;
    rest: RestService;

    constructor() {
        this.rest = new RestService();
    }
    
    public static isLoggedIn() {
        return false;
    }

    // Return data:
    // {
    //     success: boolean;
    //     errorCode: number;
    //     message: string;
    //     token: string;
    // }
    public login(username: string, password: string, cb: any):any {
        this.rest.request('POST', '/api/auth/login', {
            'username': username, 'password': password
        }, (data:any) => {
            cb (data);
        }, (message:any) => {
            cb (message);
        });

    }
 
}
