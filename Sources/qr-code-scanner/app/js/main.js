import QRReader from './vendor/qrscan.js';
import { snackbar } from './snackbar.js';
import styles from '../css/styles.css';
import isURL from 'is-url';
import $ from 'jquery';

//If service worker is installed, show offline usage notification
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker
      .register('/service-worker.js')
      .then(reg => {
        console.log('SW registered: ', reg);
        if (!localStorage.getItem('offline')) {
          localStorage.setItem('offline', true);
          snackbar.show('App is ready for offline usage.', 0);
        }
      })
      .catch(regError => {
        console.log('SW registration failed: ', regError);
      });
  });
}

window.addEventListener('DOMContentLoaded', () => {
  //To check the device and add iOS support
  window.iOS = ['iPad', 'iPhone', 'iPod'].indexOf(navigator.platform) >= 0;
  window.isMediaStreamAPISupported = navigator && navigator.mediaDevices && 'enumerateDevices' in navigator.mediaDevices;
  window.noCameraPermission = false;

  var copiedText = null;
  var frame = null;
  var selectPhotoBtn = document.querySelector('.app__select-photos');
  var Openfile = document.querySelector('.app__select-fichier');
  var dialogElement = document.querySelector('.app__dialog');
  var dialogOverlayElement = document.querySelector('.app__dialog-overlay');
  var dialogOpenBtnElement = document.querySelector('.app__dialog-open');
  var dialogCloseBtnElement = document.querySelector('.app__dialog-close');
  var scanningEle = document.querySelector('.custom-scanner');
  var textBoxEle = document.querySelector('#result');
  var helpTextEle = document.querySelector('.app__help-text');
  var infoSvg = document.querySelector('.app__header-icon svg');
  var videoElement = document.querySelector('video');
  var player = document.querySelector('#audioPlayer');
  window.appOverlay = document.querySelector('.app__overlay');

  //Initializing qr scanner
  window.addEventListener('load', event => {
    QRReader.init(); //To initialize QR Scanner
    // Set camera overlay size
    // setTimeout(() => {
    setCameraOverlay();
    if (window.isMediaStreamAPISupported) {
      scan();
    }
    // }, 1000);

    // To support other browsers who dont have mediaStreamAPI
    selectFromPhoto();
  });

  function setCameraOverlay() {
    window.appOverlay.style.borderStyle = 'solid';
  }

  function createFrame() {
    frame = document.createElement('img');
    frame.src = '';
    frame.id = 'frame';
  }

  //Dialog close btn event
  dialogCloseBtnElement.addEventListener('click', hideDialog, false);
  dialogOpenBtnElement.addEventListener('click', openInBrowser, false);

  //To open result in browser
  function openInBrowser() {
    console.log('Result: ', copiedText);
    window.open(copiedText, '_blank', 'toolbar=0,location=0,menubar=0');
    copiedText = null;
    hideDialog();
  }

  // Send code to server
  function sendCode(code) {
    $.get('http://localhost:3843/barcode?code=' + code);
  }

  //Hide dialog
  function hideDialog() {
    copiedText = null;
    textBoxEle.value = '';

    if (!window.isMediaStreamAPISupported) {
      frame.src = '';
      frame.className = '';
    }

    dialogElement.classList.add('app__dialog--hide');
    dialogOverlayElement.classList.add('app__dialog--hide');
    // scan();
  }

  //Scan
  function scan(forSelectedPhotos = false) {
    if (window.isMediaStreamAPISupported && !window.noCameraPermission) {
      scanningEle.style.display = 'block';
    }

    if (forSelectedPhotos) {
      scanningEle.style.display = 'block';
    }

    QRReader.scan(result => {
      copiedText = result;
      textBoxEle.value = result;
      textBoxEle.select();
      scanningEle.style.display = 'none';

      // play sound beep
      player.play();
      sendCode(result);

      function beep() {
        var beep = new Audio();
        beep.src = 'beep-01a.mp3';
        beep.play();
      }
      sendCode(result);

      setTimeout(() => {
        scan(false);
        hideDialog();
      }, 3000);

      if (isURL(result)) {
        dialogOpenBtnElement.style.display = 'inline-block';
      }
      dialogElement.classList.remove('app__dialog--hide');
      dialogOverlayElement.classList.remove('app__dialog--hide');
      const frame = document.querySelector('#frame');
      // if (forSelectedPhotos && frame) frame.remove();
    }, forSelectedPhotos);
  }

  function selectFromPhoto() {
    //Creating the camera element
    var camera = document.createElement('input');
    camera.setAttribute('type', 'file');
    camera.setAttribute('capture', 'camera');
    camera.id = 'camera';
    window.appOverlay.style.borderStyle = '';
    selectPhotoBtn.style.display = 'block';
    createFrame();

    //Add the camera and img element to DOM
    var pageContentElement = document.querySelector('.app__layout-content');
    pageContentElement.appendChild(camera);
    pageContentElement.appendChild(frame);

    //Click of camera fab icon
    selectPhotoBtn.addEventListener('click', () => {
      scanningEle.style.display = 'none';
      document.querySelector('#camera').click();
    });

    //On camera change
    camera.addEventListener('change', event => {
      if (event.target && event.target.files.length > 0) {
        frame.className = 'app__overlay';
        frame.src = URL.createObjectURL(event.target.files[0]);
        if (!window.noCameraPermission) scanningEle.style.display = 'block';
        window.appOverlay.style.borderColor = 'rgb(62, 78, 184)';
        scan(true);
      }
    });
  }

  function Qrfile() {
    var camera = document.createElement('input');
    camera.setAttribute('type', 'file');
    camera.id = 'fichier';
    window.appOverlay.style.borderStyle = '';
    createFrame();

    //Add the camera and img element to DOM
    var pageContentElement = document.querySelector('.app__layout-content');
    pageContentElement.appendChild(camera);
    pageContentElement.appendChild(frame);

    //Click of camera fab icon
    Openfile.addEventListener('click', () => {
      scanningEle.style.display = 'none';
      document.querySelector('#fichier').click();
    });
  }
});
