import {Controller} from './BasicController';
import { stringify } from 'querystring';
import { isUndefined } from 'util';
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
                settings.set("account_info.userid", respond['id']);
                settings.set("account_info.fullname", respond['name']);
                settings.set("account_info.username", respond['username']);
                // settings.set("account_info.roles", respond['role']);
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
        settings.delete("account_info.roles");
        settings.set('verified_logged_in', false);
    }


    public getLoggedInUserDisplayName():string {
        let fullname = settings.get("account_info.fullname");
        let username = settings.get("account_info.username");
        let userid = settings.get("account_info.userid");
        if (fullname != null && !isUndefined(fullname) && fullname != "" && fullname != "null") {
            return fullname;
        } else if (username != null && !isUndefined(username) && username != "" && username != "null") {
            return username;
        } else {
            return userid;
        }
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

    public deleteUser(userId: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('DELETE', '/api/v1/users/' + userId, null, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public updateUserInfo(data: any, cbSuccess: (any), cbFail: (any)) {
        // console.log(data);
        // console.log('/api/v1/users/' + data.id);
        this.getRestService().request('PUT', '/api/v1/users/' + data.id, data, (respond:any) => {
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
