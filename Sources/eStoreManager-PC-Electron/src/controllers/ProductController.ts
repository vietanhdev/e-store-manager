import {Controller} from './BasicController';
import { stringify } from 'querystring';
const settings = require('electron-settings');


export class ProductController extends Controller {

    public SEARCH_API_URL: string = "/api/v1/search/products";

    constructor() {
        super();
    }


    public getAjaxAPIUrl() {
        return this.getRestService().getUrl(this.SEARCH_API_URL);
    }
    

    public getAllProducts(cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/products', {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond.products);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    
    public getProductDataById(product_id: string, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('GET', '/api/v1/products/' + product_id, {}, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public getProductDataByBarcode(barcode: string, cbSuccess: (any), cbFail: (any)) {
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

    public getProductData(productIdOrBarcode: string, cbSuccess: (any), cbFail: (any)) {

        // Try to get product by id
        this.getProductDataById(productIdOrBarcode,
            (respond:any) => { // Success
                cbSuccess(respond);
            },  
            (respond:any) => { // Fail
                // If failed, try to find product using barcode
                this.getProductDataByBarcode(productIdOrBarcode,
                    (respond:any) => { // Success
                        cbSuccess(respond);
                    },  
                    (respond:any) => { // Fail
                        cbFail(respond);
                    });
            });
    }

    public addProduct(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('POST', '/api/v1/products', data, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public deleteProduct(productId: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('DELETE', '/api/v1/products/' + productId, null, (respond:any) => {
            if (respond['success'] == true) {
                cbSuccess (respond);
            } else {
                cbFail(respond);
            }
        }, (respond:any) => {
            cbFail (respond);
        }, true);
    }

    public updateProductInfo(data: any, cbSuccess: (any), cbFail: (any)) {
        this.getRestService().request('PUT', '/api/v1/products/' + data.id, data, (respond:any) => {
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
