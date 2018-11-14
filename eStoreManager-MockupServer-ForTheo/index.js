// Import express
let express = require('express');
// Import Body parser
let bodyParser = require('body-parser');
// Initialize the app
let app = express();
// Configure bodyparser to handle post requests
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

// Setup server port
var port = 8080;
// Send message for default URL
app.get('/', (req, res) => res.send('Hello World with Express'));


// Login
app.get('/api/product_code', (req, res) => {

    let product_code = req.query.code;
  
    console.log(product_code);

    res.send('ok');

});



// Launch app to listen to specified port
app.listen(port, function () {
    console.log("Running RestHub on port " + port);
});
