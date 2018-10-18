// import { LoadingModal } from "../views/LoadingModal"
const {ipcMain} = require('electron');


console.log(ipcMain);

class LoginView {

    loadingModal: any;

    constructor() {

        // this.loadingModal = new LoadingModal();

        ipcMain.on('synchronous-message', (event:any, arg:any) => {
            // console.log(event);
            console.log( arg.username + arg.password );
        });
        
    
    }

}

var loginView = new LoginView();