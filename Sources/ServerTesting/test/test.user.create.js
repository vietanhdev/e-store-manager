var config = require("./config.json")
var auth = require("./auth.json")
var chakram = require('chakram')
var expect = require('chakram').expect


let headers = Object({'Content-Type': 'application/json'});
headers['Authorization'] = "Bearer " + auth.admin_token;
var admin_req_options = {
    headers : headers
}

describe("Test Users APIs", function () {
    it("Create new user without login => fail", function () {
        let data =  {
            "name": "ceo la",
            "username": "newuser01",
            "password": "ahsdguyih29387ey3892767565jihi7897897",
            "salary": 1000,
            "email": "ceo@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167558",
            "roles": [
                "ROLE_ADMIN", "ROLE_ADMIN"
            ]
        };

        // console.log(admin_req_options);
        return chakram.post(config.server_url + "/api/v1/users", data, {}).then(function (response) {
            expect(response).to.have.status(200);
            expect(response.body.success).to.equal(false);
            expect(response.body).to.not.have.property("id");
        }).then(() => {

        });

    });

    it("Create new user without username => fail", function () {
        let data =  {
            "name": "ceo la",
            "password": "ahsdguyih29387ey3892767565jihi7897897",
            "salary": 1000,
            "email": "ceo@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167558",
            "roles": [
                "ROLE_ADMIN", "ROLE_ADMIN"
            ]
        };

        // console.log(admin_req_options);
        return chakram.post(config.server_url + "/api/v1/users", data, admin_req_options).then(function (response) {
            expect(response).to.have.status(200);
            expect(response.body.success).to.equal(false);
            expect(response.body).to.not.have.property("id");
            expect(response.body).to.have.property("code");
        }).then(() => {

        });

    });



    it("Create new user with valid info", function () {
        let data =  {
            "name": "ceo la",
            "username": "newuser0ass1s",
            "password": "ahsdguyih29387ey3892767565jihi7897897",
            "salary": 1000,
            "email": "ceoss@gmail.com",
            "address": "hoa binh",
            "mobileNo": "0916167558",
            "roles": [
                "ROLE_ADMIN", "ROLE_ADMIN"
            ]
        };

        // console.log(admin_req_options);
        return chakram.post(config.server_url + "/api/v1/users", data, admin_req_options).then(function (response) {
            console.log(response.body);
            expect(response).to.have.status(200);
            expect(response.body.success).to.equal(true);
            expect(response.body).to.have.property("id");
        }).then((response) => {
            it("Delete created user", () => {
                chakram.del(config.server_url + "/api/v1/users/" + response.body.id, data, admin_req_options).then(function (response) {
                    expect(response).to.have.status(200);
                    expect(response.body.success).to.equal(true);
                });
            })
        });

    });

    
}); 


describe("Test Login API", function () {

});

