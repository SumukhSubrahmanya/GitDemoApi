import java.util.List;

import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(payload.CoursePrice());
		/*
		 * 1. Print No of courses returned by API
		 * 
		 * 2.Print Purchase Amount
		 * 
		 * 3. Print Title of the first course
		 * 
		 * 4. Print All course titles and their respective Prices
		 * 
		 * 5. Print no of copies sold by RPA Course
		 * 
		 * 6. Verify if Sum of all Course prices matches with Purchase Amount
		 */

		int coursesCount = js.getInt("courses.size()");
		System.out.println("1.Print No of courses returned by API = "+coursesCount);
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("2.Print Purchase Amount = "+purchaseAmount);
		
		String title = js.getString("courses[0].title");
		System.out.println("3.Print Title of the first course = "+title);
		
		//List<String> titles[]= new List<String>;
		System.out.println("4.Print All course titles and their respective Prices");
		for(int i=0;i<coursesCount;i++)
		{
			String courseTitles = js.get("courses["+i+"].title");
			int coursePrice = js.getInt("courses["+i+"].price");
			System.out.println("Course title = "+courseTitles+" Price = "+coursePrice);
		}
		
		//5. Print no of copies sold by RPA Course
		//System.out.println(js.getInt("courses[2].copies"));
		for(int i=0;i<coursesCount;i++)
		{
		String courseTitles = js.get("courses["+i+"].title");
		if(courseTitles.equalsIgnoreCase("RPA"))
		{
			int copy = js.getInt("courses["+i+"].copies");
			System.out.println("5. Print no of copies sold by RPA Course ="+copy);
			break;
		}
		
		}
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int sum=0;
		int sumproduct=0;
		for(int i=0;i<coursesCount;i++)
		{
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			sumproduct=price*copies;
			sum+=sumproduct;			
		}
		
		Assert.assertEquals(js.getInt("dashboard.purchaseAmount"), sum);
		System.out.println("Sum Of Course Prices = "+sum);
		
	}

}
