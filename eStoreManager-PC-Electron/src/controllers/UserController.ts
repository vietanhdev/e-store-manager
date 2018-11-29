import {Controller} from './BasicController';
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
        this.getRestService().request('GET', '/api/v1/me', {}, (respond:any) => {
            if (respond.success == true) {
                settings.set("account_info.userid", respond['user_id']);
                settings.set("account_info.fullname", respond['name']);
                settings.set("account_info.username", respond['username']);
                cbSuccess();
            } else {
                cbFail(respond.message);
            }
        }, (respond:any) => {
            cbFail(respond.message);
        }, true);
        
    }

    // Return data:
    // {
    //     success: boolean;
    //     errorCode: number;
    //     message: string;
    //     token: string;
    // }
    public login(username: string, password: string, cbSuccess: any, cbFail: any):any {
        this.getRestService().request('POST', '/api/v1/login', {
            'username': username, 'password': password
        }, (respond:any) => {
            if (respond.success == true) {
                cbSuccess(respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, false);

    }


    public logout() {
        settings.delete("account_info.token");
        settings.delete("account_info.userid");
        settings.delete("account_info.username");
        settings.delete("account_info.fullname");
    }


    public getAllUsers(cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/users', {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond.users);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }


    public addUser(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('POST', '/api/v1/users', data, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }
 
}
