import {Controller} from '../Controller/Controller';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class UserController extends Controller {

    private token: string;

    constructor() {
        super();
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
        this.getRestService().request('POST', '/api/auth/login', {
            'username': username, 'password': password
        }, (data:any) => {
            cb (data);
        }, (message:any) => {
            cb (message);
        });

    }
 
}
