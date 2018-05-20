package lab2;
//Создание JSON файла filecompany.json
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJSONFile {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		JSONObject obj = new JSONObject();
		obj.put("Name", "crunchify.com");
		obj.put("Author", "App Shah");

		JSONArray company = new JSONArray();
		company.add("Company: eBay");
		company.add("Company: Paypal");
		company.add("Company: Google");
		obj.put("Company List", company);

		
		try (FileWriter file = new FileWriter("filecompany.json")) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
	}
}
