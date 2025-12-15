package com.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.api.utils.ExtentListener;
import io.restassured.http.ContentType;

public class CreateUserTest {

    @Test
    public void verifyCreateUserAPI() {
        ExtentListener.logInfo("🔹 Sending POST Request to create user...");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        String requestBody = "{ \"title\": \"Automation Tester\", \"body\": \"Learning API Automation with Rest Assured\", \"userId\": \"101\" }";

        ExtentListener.logInfo("📝 Request Body: " + requestBody);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract().response();

        ExtentListener.logInfo("🔹 Status Code: " + response.getStatusCode());
        ExtentListener.logInfo("🔹 Response Body: " + response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201, "Expected 201 Created");
    }
}