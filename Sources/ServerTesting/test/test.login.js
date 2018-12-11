var config = require("./config.json")
var chakram = require('chakram')
var expect = require('chakram').expect
var token = null;

describe("Test Login API", function () {
    it("Login with correct username and password should return correct response", function () {
        let data =  {username: config.default_admin,
            password: config.default_admin_password}
        return chakram.post(config.server_url + "/api/v1/login", data).then(function (response) {
            expect(response).to.have.status(200);
            expect(response.body.success).to.equal(true);
            expect(response.body).to.have.property("id");
            expect(response.body).to.have.property("name");
            expect(response.body).to.have.property("username");
            expect(response.body).to.have.property("salary");
            expect(response.body).to.have.property("email");
            expect(response.body).to.have.property("tokenType");
            expect(response.body).to.have.property("accessToken");
            token = response.body.accessToken;
        });

    });


    it("Login without password shoule return argument_not_valid", function () {
        let options =  {
            body: {username: config.default_admin}
        }  
        return chakram.post(config.server_url + "/api/v1/login", options).then(function (response) {
            expect(response).to.have.status(200);
            expect(response.body.success).to.equal(false);
            expect(response.body).to.not.have.property("accessToken");
            expect(response.body.code).to.equal("argument_not_valid");
            token = response.body.accessToken;
        });
    });

    it("Login without username shoule return argument_not_valid", function () {
        let options =  {
            body: {password: config.default_admin_password}
        }  
        return chakram.post(config.server_url + "/api/v1/login", options).then(function (response) {
            expect(response).to.have.status(200);
            expect(response.body.success).to.equal(false);
            expect(response.body).to.not.have.property("accessToken");
            expect(response.body.code).to.equal("argument_not_valid");
            token = response.body.accessToken;
        });
    });

    
}); 


describe("Test Login API", function () {

});

