var config = require("./config.json")


describe("Test Login API", function () {
    it("should make HTTP assertions easy", function () {
        var response = chakram.post(config.server_url + "/api/v1/login");
        expect(response).to.have.status(200);
        expect(response).to.have.header("content-type", "application/json");
        expect(response).not.to.be.encoded.with.gzip;
        expect(response).to.comprise.of.json({
        args: { test: "chakram" }
        });
        return chakram.wait();
    });
}); 