import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import files.ReUsableMethods;

public class JiraBugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="https://sumukhsubrahmanya.atlassian.net";
		String createIssueResponse = given()
		.header("Content-Type","application/json")
		.header("Authorization","Basic c3VtdWtoLnN1YnJhaG1hbnlhQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjA2ZVZhMmRUOFdWbkNsOEY4eXQ5MHNsT3hfNWExX2Itam5FblBsaExsNUlJVFA5Y1Qta05VTURYNHNkTUI5RXZOUVp0MXVHTUZXUGdrWmZDU3gxV3NLcE5Sc3UyRmNKeUVDejkxWWYxZFZfNzZfdFdjNjN5NUpwR2hlRXRtQmRPVFBLZDNVMFN5VjVQblBXVHlKaEM1cC1Xc01wSFZQWW83aVJERVphX01Salk9MjI0NDdENEU=")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Links: Links are not working-automation\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}").log().all()
		.when().post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(createIssueResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);
		
		//Add Attachment to the created issue
		String attachFileResponse = given().log().all()
		.pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic c3VtdWtoLnN1YnJhaG1hbnlhQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjA2ZVZhMmRUOFdWbkNsOEY4eXQ5MHNsT3hfNWExX2Itam5FblBsaExsNUlJVFA5Y1Qta05VTURYNHNkTUI5RXZOUVp0MXVHTUZXUGdrWmZDU3gxV3NLcE5Sc3UyRmNKeUVDejkxWWYxZFZfNzZfdFdjNjN5NUpwR2hlRXRtQmRPVFBLZDNVMFN5VjVQblBXVHlKaEM1cC1Xc01wSFZQWW83aVJERVphX01Salk9MjI0NDdENEU=")
		.multiPart("file",new File("C:\\Users\\Sumukh\\Desktop\\Selenium Screenshots\\screenshot.png")).log().all()
		.post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}

}
