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

    
    public getProductTypeDataById(product_id: string, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/product_types/' + product_id, {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public getProductTypeDataByBarcode(barcode: string, cbSuccess: (any), cbFail: (any)) {
        let postData: any = {};
        postData.draw = 1;
        postData.length = 1;
        postData.search = {};
        postData.search.barcode = barcode;
        postData.start = 0;
        this.getRestService().request('POST', this.SEARCH_API_URL, postData, (respond:any) => {
            console.log(Number(respond['recordsFiltered']));
            if (Number(respond['recordsFiltered']) == 1) {
                cbSuccess (respond.data[0]);
            } else {
                cbFail({"success": false});
            }
        }, (respond:any) => {
            console.log(respond);
            cbFail (respond);
        }, true);
    }

    public getProductTypeData(productIdOrBarcode: string, cbSuccess: (any), cbFail: (any)) {

        // Try to get product by id
        this.getProductTypeDataById(productIdOrBarcode,
            (respond:any) => { // Success
                cbSuccess(respond);
            },  
            (respond:any) => { // Fail
                // If failed, try to find product using barcode
                this.getProductTypeDataByBarcode(productIdOrBarcode,
                    (respond:any) => { // Success
                        cbSuccess(respond);
                    },  
                    (respond:any) => { // Fail
                        cbFail(respond);
                    });
            });
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
