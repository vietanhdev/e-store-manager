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
app.post('/api/v1/login', (req, res) => {

console.log(req);

    let username = req.body.username;
    let password = req.body.password;

    if (username == "admin" && password == "admin") {
        res.json({
            'success': true,
            'token': 'asdj8932ud920d9032ui39'
        });
    } else {
        res.json({
            'success': false,
            'errorCode': 101,
            'message': 'Wrong username or password'
        });
    }

});



// Launch app to listen to specified port
app.listen(port, function () {
    console.log("Running RestHub on port " + port);
});
