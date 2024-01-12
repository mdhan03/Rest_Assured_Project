package com.rest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Epic("Epic-01")
@Feature("Basic API Automation")

public class APIAutomation{
	
	@BeforeClass
	public void beforeClass() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
				setBaseUri("https://79ddc7c0-e960-43da-ac37-d0ceca0899fa.mock.pstmn.io").
				//addHeader("X-API-Key", "PMAK-65523acce6e34a00316ff562-b16e73a7ef763ec0caa2b49a184a1565ed").
				setContentType(ContentType.JSON).
				log(LogDetail.ALL);
		RestAssured.requestSpecification = requestSpecBuilder.build();
		
		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
				//expectContentType(ContentType.JSON).
				log(LogDetail.ALL);
		RestAssured.responseSpecification = responseSpecBuilder.build();
		
			}
	
	
	@Story("Story-01")
	@Test
	@Description("GET API")
	@Severity(SeverityLevel.NORMAL)
	public void validate_response() {
		given().filter(new AllureRestAssured()).
		when().
			get("/id=123").
		then().
			log().body().
			assertThat().
			statusCode(200);
	} 
			
	@Story("Story-01")
	@Test
	@Description("GET API")
	@Severity(SeverityLevel.NORMAL)
	public void extract_resp() {
		Response resp = given().filter(new AllureRestAssured()).
		log().headers().
	when().
		get("/id=123").
	then().
		log().ifValidationFails().
		assertThat().
		statusCode(200).
		extract().response();
		
		System.out.println("Response is " +resp.asString());
	}
	
	
	
	@Story("Story-01")
	@Test
	@Description("GET API")
	@Severity(SeverityLevel.NORMAL)
	public void extract_single_resp() {
			Response name = given().filter(new AllureRestAssured()).
			when().
			get("/id=123").
			then().
			log().all().
			assertThat().
			statusCode(200).
			extract().
			response(); 
			
			JsonPath jsonpath = new JsonPath(name.asString());
			System.out.println("name" +jsonpath.getString("username"));		
	}
	
	
	@Story("Story-01")
	@Test
	@Description("POST API")
	@Severity(SeverityLevel.NORMAL)
	public void validate_post_request() {
		String payload = "{\r\n"
				+ "  \"userId\": 1234,\r\n"
				+ "  \"username\": \"john_doe1\",\r\n"
				+ "  \"email\": \"john.doe1@example.com\"\r\n"
				+ "}";
		given().filter(new AllureRestAssured()).
		body(payload).
		when().
		post("/id").
		then().assertThat().
		statusCode(200).
		body("username", hasItem("Entry successfully added"));
				
	}
	
	
	@Story("Story-01")
	@Test
	@Description("DELETE API")
	@Severity(SeverityLevel.NORMAL)
	public void validate_delete_request() {
	given().filter(new AllureRestAssured()).
	when().
	delete("/id=1234").
	then().
	assertThat().
	body("username", hasItem("Entry successfully deleted"));
			
	}
	
}
	

	
