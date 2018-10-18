// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.

'use strict'


interface Window {
    $: any;
    jQuery: any;
    Tether: any;
    Bootstrap:any;
}


(<any>window).$ = (<any>window).jQuery = require('jquery')
(<any>window).Tether = require('tether')
(<any>window).Bootstrap = require('bootstrap')