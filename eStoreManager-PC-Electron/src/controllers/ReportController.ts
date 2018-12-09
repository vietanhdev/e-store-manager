import {Controller} from './BasicController';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class ReportController extends Controller {

   
    constructor() {
        super();
    }


    public getReport(dateFrom: Date, dateTo: Date, cbSuccess: (any), cbFail: (any)) {
        // this.getRestService().request('GET', '/api/v1/sells', {}, (respond:any) => {
        //     if (respond['success'] == true) {
        //         cbSuccess (respond.sells);
        //     } else {
        //         cbFail(respond);
        //     }
        // }, (respond:any) => {
        //     cbFail (respond);
        // }, true);
    }

 
}
