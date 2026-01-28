package files;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void AddBook(String isbn,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
		.body(payload.AddBook(isbn,aisle)).when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"fg8","srgjk8"},{"fssg","gheha8"},{"hjsfs8","hfjju"}};
	}
	
	
	@Test(dataProvider="BooksData")
	public void DeleteBook(String isbn,String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().queryParam("ID",payload.DeleteBook(isbn, aisle)).header("Content-Type","application/json")
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
	}

}
