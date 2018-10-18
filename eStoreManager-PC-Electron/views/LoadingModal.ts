export class LoadingModal {

    constructor() {

        document.write(
            `
            <div id="loading-screen" style="display: none; z-index: 999; position:absolute; top:0; bottom: 0; left: 0; right: 0; background: rgba(0,0,0,0.5);">
                <img style="display: block; margin-top: 15%; margin-left: auto; margin-right: auto; z-index: 1000;" src="images/loading.svg">
                <h5 style="text-align:center; color: #ffe;">Please wait ...</h5>
            </div>
            `
        );

    }

    show() {
        let loadingModal = document.getElementById('loading-screen');
        loadingModal.style.display = "block";
    }

    hide() {
        let loadingModal = document.getElementById('loading-screen');
        loadingModal.style.display = "none";
    }

}

