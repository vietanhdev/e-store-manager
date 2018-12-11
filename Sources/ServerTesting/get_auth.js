var config = require("./test/config.json")
var chakram = require('chakram')
var expect = require('chakram').expect
var token = null;

describe("Get auth info and write to file", function () {
    it("Get auth info then write to file", function () {
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

            // Write auth info to file
            let obj = {};
            obj.admin_token = token;
            var json = JSON.stringify(obj);
            var fs = require('fs');
            fs.writeFile('./test/auth.json', json, 'utf8', () => {});

        });

    });
    
}); 

