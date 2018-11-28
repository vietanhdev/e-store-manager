import {Controller} from '../Controller/Controller';
import { stringify } from 'querystring';
const settings = require('electron-settings');

// Settings:
// account_info.userid
// account_info.fullname
// account_info.token
// account_info.username

export class UserController extends Controller {

    constructor() {
        super();
    }
    
    public isLoggedIn(cbSuccess: (any), cbFail: (any)):void {

        this.getRestService().request('GET', '/api/v1/me/profile', {}, (data:any) => {
            // console.log(data);
            if (data.success == true) {
                settings.set("account_info.userid", data['user_id']);
                settings.set("account_info.fullname", data['name']);
                settings.set("account_info.username", data['username']);
                cbSuccess();
            } else {
                cbFail();
            }
        }, (message:any) => {
            console.log(message);
            cbFail();
        }, true);
        
    }

    // Return data:
    // {
    //     success: boolean;
    //     errorCode: number;
    //     message: string;
    //     token: string;
    // }
    public login(username: string, password: string, cb: any):any {
        this.getRestService().request('POST', '/api/v1/login', {
            'username': username, 'password': password
        }, (data:any) => {
            cb (data);
        }, (message:any) => {
            cb (message);
        }, false);

    }


    public logout() {
        settings.delete("account_info.token");
        settings.delete("account_info.userid");
        settings.delete("account_info.username");
        settings.delete("account_info.fullname");
    }


    public getAllUsers(cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/users', {}, (data:any) => {
            if (data['success'] == true) {
                cbSuccess (data.users);
            } else {
                cbFail(data);
            }
        }, (message:any) => {
            cbFail (message);
        }, true);
    }
 
}
