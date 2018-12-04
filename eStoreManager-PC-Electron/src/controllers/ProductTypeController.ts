import {Controller} from './BasicController';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class ProductTypeController extends Controller {

    public SEARCH_API_URL: string = "/api/v1/search/product_types";

    constructor() {
        super();
    }


    public getAjaxAPIUrl() {
        return this.getRestService().getUrl(this.SEARCH_API_URL);
    }
    

    public getAllProductTypes(cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/product_types', {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond.product_types);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }


    public addProductType(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('POST', '/api/v1/product_types', data, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public deleteProductType(product_typeId: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('DELETE', '/api/v1/product_types/' + product_typeId, null, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public updateProductTypeInfo(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('PUT', '/api/v1/product_types/' + data.id, data, (respond:any) => {
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
