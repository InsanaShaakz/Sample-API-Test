package com.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeviceApiTest {

    private static final String BASE_URL = "https://api.restful-api.dev/objects";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testAddNewDevice() {
        // Sample request payload
        String requestBody = "{ \"name\": \"Apple Max Pro 1TB\", \"data\": { \"year\": 2023, \"price\": 7999.99, \"CPU model\": \"Apple ARM A7\", \"Hard disk size\": \"1 TB\" } }";

        // Send POST request
        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post();

        // Validate status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch!");

        // Parse response
        String responseBody = response.getBody().asString();
        String id = response.jsonPath().getString("id");
        String createdAt = response.jsonPath().getString("createdAt");
        String name = response.jsonPath().getString("name");
        Integer year = response.jsonPath().getInt("data.year");
        Double price = response.jsonPath().getDouble("data.price");

        // Validate response details
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "CreatedAt should not be null");
        Assert.assertEquals(name, "Apple Max Pro 1TB", "Name mismatch");
        Assert.assertEquals(year, Integer.valueOf(2023), "Year mismatch");
        Assert.assertEquals(price, Double.valueOf(7999.99), "Price mismatch");
    }
}

