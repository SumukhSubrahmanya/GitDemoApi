import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Validate if Add Place API is working as expected
		//3 Principles of Rest Assured
		//1>given - all the input details 
		//2>when - Submit the API
		//3>then - validate the response
		
		
		//Add place -->Update Place with New Address --> Get Place to validate if new address is present in response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response); // for parsing JSON
		String placeId = js.getString("place_id");
		
		System.out.println("placeId = "+placeId);
		
		System.out.println("************************\n");
		
		//content of the file to string --> content of the file  can convert into Byte--> Byte data to String
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String responseJson = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Sumukh\\Desktop\\addPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response of addPlace from JSON File = "+responseJson);
		
		//PUT UpdatePlace
		
		String newAddress = "701 Summer walk India, USA, New York";
				
		String updateResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.UpdatePlace(placeId,newAddress)).when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"))
		.extract().response().asString();
		
		System.out.println(updateResponse);
		
		System.out.println("************************\n");
		
		//GetPlace
		
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress))
		.extract().response().asString();
		
		System.out.println(getResponse);
		
		//JsonPath js1 = new JsonPath(getResponse);
		JsonPath js1 = ReUsableMethods.rawToJson(getResponse);
		String actualAddress = js1.getString("address");
		System.out.println("getResponse address = "+actualAddress);
		
		//Cucumber , TestNG , JUint
		Assert.assertEquals(newAddress, actualAddress);
		

	}

}
