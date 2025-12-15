package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.api.utils.ExtentListener;

public class GetUsersTest {
	//git init
	//git remote add origin https://github.com/Naveen491/CampusAutomationAPI.git
	//git add .
	//git commit -m "Initial commit with Jenkinsfile and project"
//	git branch -M main
	//git push -u origin main
	
    @Test
    public void verifyListUsersAPI() {
        ExtentListener.logInfo("🔹 Sending GET Request to fetch users list...");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = RestAssured
                .given()
                .when()
                .get("/users")
                .then()
                .extract().response();

        ExtentListener.logInfo("🔹 Status Code: " + response.getStatusCode());
        ExtentListener.logInfo("🔹 Response Body: " + response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 OK");
    }
}