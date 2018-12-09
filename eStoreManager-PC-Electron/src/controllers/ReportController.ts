import {Controller} from './BasicController';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class ReportController extends Controller {

   
    constructor() {
        super();
    }


    public getReport(dateFrom: Date, dateTo: Date, cbSuccess: (any), cbFail: (any)) {

        let data:any = {};
        data.length = 5;
        data.start = dateFrom + " 00:00:00";
        data.end = dateTo + " 23:59:59";

        this.getRestService().request('POST', '/api/v1/reports/1', data, (respond:any) => {
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
