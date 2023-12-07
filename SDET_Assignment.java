package script;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SDET_Assignment {

	public static void main(String[] args) throws IOException, InterruptedException {
		List<LinkedHashMap<String, String>> listOfMaps = null;
		System.setProperty("webdriver.chrome.driver", "Version119\\chromedriver-win64\\chromedriver.exe");
		// change chromedriver path here
		WebDriver driver = new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//summary[text()='Table Data']")).click();
		System.out.println("Click on table data");
		WebElement element = driver.findElement(By.xpath("//p//textarea[@id='jsondata']"));
		element.clear();
		System.out.println("clear data from table");
		Thread.sleep(3000);
		String jsonData = new String(
				Files.readAllBytes(Paths.get("TestData\\InputData.JSON")));
		// change json file path here 
		System.out.println(jsonData);
		element.sendKeys(jsonData);
		System.out.println("set data in table ");
		driver.findElement(By.xpath("//button[@id=\"refreshtable\"]")).click();
		// Create an ObjectMapper instance (Jackson)
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Convert JSON array string to List of HashMaps
			// convert json data from array
			listOfMaps = objectMapper.readValue(jsonData, new TypeReference<List<LinkedHashMap<String, String>>>() {
			});

			// Display the list of HashMaps
			for (LinkedHashMap<String, String> map : listOfMaps) {
//                System.out.println("HashMap: " + map);
				// Access elements in each HashMap
				System.out.println("Name: " + map.get("name"));
				System.out.println("Age: " + map.get("age"));
				System.out.println("City: " + map.get("gender"));
			}
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + listOfMaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int j = 0;

		for (int i = 1; i <= 5; i++) {
			List<WebElement> jData = driver.findElements(
					By.xpath("(//table[@id='dynamictable']/descendant::tr/following-sibling::tr)[" + i + "]/td"));
			LinkedHashMap<String, String> res = new LinkedHashMap<>();
			WebElement name1 = jData.get(0);
			WebElement age1 = jData.get(1);
			WebElement gender1 = jData.get(2);
			// add data from web table into linkedhash map
			res.put("name", name1.getText());
			res.put("age", age1.getText());
			res.put("gender", gender1.getText());

			System.out.println("data from web" + res);
			System.out.println(listOfMaps.get(j));
			// Compare the maps using equals method
			boolean mapsAreEqual = listOfMaps.get(j).equals(res);
			// compare data from json file and data from table
			if (mapsAreEqual) {
				System.out.println("The maps for index " + j + " are equal");
			} else {
				System.out.println("The maps for index " + j + " are not equal");
			}

			j++;
		}
	}
}
