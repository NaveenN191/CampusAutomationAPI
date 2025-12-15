package com.api.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {
        // Base URI will be set individually per test
        System.out.println("Base setup initialized.");
    }
}