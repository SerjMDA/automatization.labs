package lab2;
//parse JSON
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSONfile {
	public static void main(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader("filecompany.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String Author = (String) jsonObject.get("Author");
			System.out.println("Author is: " + Author);

			String Name = (String) jsonObject.get("Name");
			System.out.println("Name is: " + Name);

			//получаем данные из списка
			JSONArray companyArray = (JSONArray) jsonObject.get("Company List");
			Iterator<String> iterator = companyArray.iterator();
			
			while(iterator.hasNext()) {
				System.out.println("Company List: "+iterator.next());
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
