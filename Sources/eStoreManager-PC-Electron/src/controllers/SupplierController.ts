import {Controller} from './BasicController';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class SupplierController extends Controller {

    public SEARCH_API_URL: string = "/api/v1/search/suppliers";

    constructor() {
        super();
    }


    public getAjaxAPIUrl() {
        return this.getRestService().getUrl(this.SEARCH_API_URL);
    }
    

    public getAllSuppliers(cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/suppliers', {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond.suppliers);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }


    public addSupplier(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('POST', '/api/v1/suppliers', data, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public deleteSupplier(supplierId: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('DELETE', '/api/v1/suppliers/' + supplierId, null, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public updateSupplierInfo(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('PUT', '/api/v1/suppliers/' + data.id, data, (respond:any) => {
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
