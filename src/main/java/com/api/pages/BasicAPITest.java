package com.api.pages;

import static io.restassured.RestAssured.given;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class BasicAPITest {
	
	public static void main(String[] args) {
	// TODO Auto-generated method stub
	// validate if Add Place API is workimg as expected 
			//Add place-> Update Place with New Address -> 
		//Get Place to validate if New address is present in response
			
			//given - all input details 
			//when - Submit the API -resource,http method
			//Then - validate the response
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given()
		    .relaxedHTTPSValidation().log().all()
		    .queryParam("key", "qaclick123")
		    .header("Accept", "application/json")
		    .body("{\r\n"
		        + "  \"location\": {\r\n"
		        + "    \"lat\": -38.383494,\r\n"
		        + "    \"lng\": 33.427362\r\n"
		        + "  },\r\n"
		        + "  \"accuracy\": 50,\r\n"
		        + "  \"name\": \"Frontline house\",\r\n"
		        + "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
		        + "  \"address\": \"70 Summer walk, USA\",\r\n"
		        + "  \"types\": [\"shoe park\", \"shop\"],\r\n"
		        + "  \"website\": \"http://google.com\",\r\n"
		        + "  \"language\": \"French-IN\"\r\n"
		        + "}")
		    .when()
		    .post("maps/api/place/add/json")
		    .then().log().all()
		    .assertThat()
		    .statusCode(200)
		    .header("server", "Apache/2.4.52 (Ubuntu)")
		    .extract().response().asString();

		System.out.println("Printing the Response body: ==> " + response);

		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println("Place ID: " + placeId);

			
			
			  //Update Place 
			String newAddress = "Summer Walk, Africa";
			  
			 given() 
			  	.log().all() 
			  	.queryParam("key","qaclick123") 
			  	.header("Content-Type","application/json") 
			  	.body("{\r\n" +
			  "\"place_id\":\""+placeId+"\",\r\n" + "\"address\":\""+newAddress+"\",\r\n" +
			  "\"key\":\"qaclick123\"\r\n" + "}")
			 .when()
			  	.put("maps/api/place/update/json") 
			 .then() 
			  	.assertThat() 
			  	.statusCode(200)
			  	.log().all();
			 
			//Get Place
			
			  String getPlaceResponse= given().relaxedHTTPSValidation()
					  .log().all()
					  .queryParam("key", "qaclick123")
					  .queryParam("place_id",placeId) .when().get("maps/api/place/get/json")
			  .then()
			  		.assertThat().log().all()
			  		.statusCode(200)
			  		.extract().response().asString(); 
			  
		//	  JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse); 
			  JsonPath js1=new JsonPath(getPlaceResponse);
			  String actualAddress =js1.getString("address"); 
			  System.out.println(actualAddress);
			  Assert.assertEquals(actualAddress, newAddress); //Cucumber Junit, Testng
			 		
		
		
		
		
		
		
		
		

			
			
			
			
			
			
			
			
			
			
	}

}
