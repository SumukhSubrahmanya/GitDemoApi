package files;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//For Query 
		int characterId=20945;
		String response = given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"query ($characterId: Int!, $episodeId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId: 28342) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Sanju\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"zee5\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":18381}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().log().all().extract().response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName,"PetsForLife");
		
		//Mutations
		String newCharacterName="PetsForLife";
		String mutationResponse = given().log().all().header("Content-Type","application/json")
				.body("{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!)\\n{\\n  createLocation(location:{name:$locationName,type:\\\"South Zone\\\",dimension:\\\"340x560\\\"})\\n  {\\n    id\\n  }\\n  \\n    createCharacter(character:{name:$characterName,type:\\\"cricket\\\",status:\\\"alive\\\",species:\\\"homo sapian\\\",gender:\\\"Male\\\",image:\\\"jpeg\\\",originId:27588,locationId:27588})\\n  {\\n    id\\n  }\\n\\t  \\n\\tcreateEpisode(episode:{name:$episodeName,air_date:\\\"1st Jan 2026\\\",episode:\\\"zee5\\\"})\\n  {\\n    id\\n  }\\n  \\n  deleteLocations(locationIds:[27588])\\n  {\\n    locationsDeleted\\n  }\\n  \\n\\n}\",\"variables\":{\"locationName\":\"Aussie\",\"characterName\":\"+newCharacterName+\",\"episodeName\":\"The Intern\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().log().all().extract().response().asString();
		
		System.out.println(mutationResponse);
		//JsonPath js = new JsonPath(response);
	}

}
