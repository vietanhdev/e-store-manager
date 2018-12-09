import {Controller} from './BasicController';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class InvoiceController extends Controller {

    public SEARCH_API_URL: string = "/api/v1/search/invoices";

    constructor() {
        super();
    }


    public getAjaxAPIUrl() {
        return this.getRestService().getUrl(this.SEARCH_API_URL);
    }
    

    public getAllInvoices(cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/invoices', {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond.invoices);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }


    public addInvoice(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('POST', '/api/v1/invoices', data, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public deleteInvoice(invoiceId: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('DELETE', '/api/v1/invoices/' + invoiceId, null, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public updateInvoiceInfo(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('PUT', '/api/v1/invoices/' + data.id, data, (respond:any) => {
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
