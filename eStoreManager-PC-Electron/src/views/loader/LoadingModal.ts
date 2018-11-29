document.write(`
    <div id="loading-screen-modal" style="display: none; z-index: 999; position:absolute; top:0; bottom: 0; left: 0; right: 0; background: rgba(0,0,0,0.5);">
        <img style="display: block; margin-top: 15%; margin-left: auto; margin-right: auto; z-index: 1000;" src="../../shared/images/loading.svg">
        <h5 style="text-align:center; color: #ffe;">Please wait ...</h5>
    </div>
`);


if (typeof ipcRenderer == "undefined") {
    const electron = require('electron');
    var ipcRenderer  = electron.ipcRenderer;
}
ipcRenderer.on('loading-modal' , function(event:any , data:any){
    let loadingModal = document.getElementById('loading-screen-modal');
    console.log(status);
    if (data.status == "show") {
        loadingModal.style.display = "block";
    } else {
        loadingModal.style.display = "none";
    }
});

