package com.qant.customerprofilemanagement.controller;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.Base64;
import org.junit.Test;
import com.qant.customerprofilemanagement.IntegrationTestBase;

public class CustomerControllerIT extends IntegrationTestBase {

	@Test
    public void addCustomer_shouldReturn200ForValidRequest() {
		String accessToken = 
		given()
			 .urlEncodingEnabled(true)
			.param("grant_type", "password")
			.param("username", "testuser2")
			.param("password", "password2")
	        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("trusted-app:secret".getBytes())).
		 when()
		    .post("/oauth/token").
		 then()
		    .log().all()
		    .statusCode(200)
		    .extract().response().jsonPath().getString("access_token");

        given()
               .contentType(APPLICATION_JSON_VALUE)
               .header("Authorization", "Bearer " + accessToken)
               .body(loadJsonFile("ValidRequest.json")).
        when()
               .post("/customer").
        then()
               .log().all()
               .statusCode(200);
    }
	
	@Test
    public void updateCustomer_shouldReturn200ForValidRequest() {
		String accessToken = given()
			 .urlEncodingEnabled(true)
			.param("grant_type", "password")
			.param("username", "testuser2")
			.param("password", "password2")
	        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("trusted-app:secret".getBytes())).
		 when()
		    .post("/oauth/token").
		 then()
		    .log().all()
		    .statusCode(200)
		    .extract().response().jsonPath().getString("access_token");

		given()
		        .contentType(APPLICATION_JSON_VALUE)
		        .header("Authorization", "Bearer " + accessToken)
		        .body(loadJsonFile("ValidRequest.json")).
		 when()
		        .post("/customer").
		 then()
		        .log().all()
		        .statusCode(200);
		
        given()
               .contentType(APPLICATION_JSON_VALUE)
               .header("Authorization", "Bearer " + accessToken)
               .body(loadJsonFile("ValidRequest.json")).
        when()
               .put("/customer/1").
        then()
               .log().all()
               .statusCode(200);
    }
	
	@Test
    public void deleteCustomer_shouldReturn200ForValidRequest() {
		String accessToken = given()
			 .urlEncodingEnabled(true)
			.param("grant_type", "password")
			.param("username", "testuser2")
			.param("password", "password2")
	        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("trusted-app:secret".getBytes())).
		 when()
		    .post("/oauth/token").
		 then()
		    .log().all()
		    .statusCode(200)
		    .extract().response().jsonPath().getString("access_token");

		given()
		        .contentType(APPLICATION_JSON_VALUE)
		        .header("Authorization", "Bearer " + accessToken)
		        .body(loadJsonFile("ValidRequest.json")).
		 when()
		        .post("/customer").
		 then()
		        .log().all()
		        .statusCode(200);
		
        given()
               .contentType(APPLICATION_JSON_VALUE)
               .header("Authorization", "Bearer " + accessToken)
               .body(loadJsonFile("ValidRequest.json")).
        when()
               .delete("/customer/1").
        then()
               .log().all()
               .statusCode(200);
    }
	
	@Test
    public void updateCustomer_shouldReturn404ForRequest() {
		String accessToken = given()
			 .urlEncodingEnabled(true)
			.param("grant_type", "password")
			.param("username", "testuser2")
			.param("password", "password2")
	        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("trusted-app:secret".getBytes())).
		 when()
		    .post("/oauth/token").
		 then()
		    .log().all()
		    .statusCode(200)
		    .extract().response().jsonPath().getString("access_token");

        given()
               .contentType(APPLICATION_JSON_VALUE)
               .header("Authorization", "Bearer " + accessToken)
               .body(loadJsonFile("ValidRequest.json")).
        when()
               .put("/customer/1000").
        then()
               .log().all()
               .statusCode(404);
    }
	
	@Test
    public void updateCustomer_InValidArguments() {
		String accessToken = given()
			 .urlEncodingEnabled(true)
			.param("grant_type", "password")
			.param("username", "testuser2")
			.param("password", "password2")
	        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("trusted-app:secret".getBytes())).
		 when()
		    .post("/oauth/token").
		 then()
		    .log().all()
		    .statusCode(200)
		    .extract().response().jsonPath().getString("access_token");

        given()
               .contentType(APPLICATION_JSON_VALUE)
               .header("Authorization", "Bearer " + accessToken)
               .body(loadJsonFile("ValidRequest.json")).
        when()
               .put("/customer/ABCD").
        then()
               .log().all()
               .statusCode(400);
    }
	
}