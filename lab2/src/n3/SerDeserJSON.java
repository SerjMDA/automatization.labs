package n3;

import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerDeserJSON {
       private static File jsonfile = new File("fileserdeser.json");
       private static ObjectMapper mapper  = new ObjectMapper();

    public static void main(String[] args) throws IOException {



        //Creating firs test object
        Initials initials1 = new Initials("Victor", "BigBoy", "Worsnop");
        contactInfo contactInfo1 = new contactInfo("victorn1@gmail.com", "3633453432");

        Person person1 = new Person(initials1, 29, "Russian", contactInfo1, new ArrayList<String>(Arrays.asList("SomePhrase1", "SomePhrase2", "SomePhrase3")));

        //Creating second test object
        Initials initials2 = new Initials("Sancho", "Petrucio", "Frankovski");
        contactInfo contactInfo2 = new contactInfo("Sancho@gmail.com", "234324535");

        Person person2 = new Person(initials2, 44, "Romanian", contactInfo2, new ArrayList<String>(Arrays.asList("SomePhrase4", "SomePhrase5", "SomePhrase6")));



       

        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);


        //to JSON file
        mapper.writeValue(jsonfile,personList);

      

        //from JSON

        String fromJson = FiletoString(new FileInputStream(jsonfile));
        List<Person> fromPerosnjson = mapper.readValue(fromJson,new TypeReference<List<Person>>(){});
        for(Person pers1 : fromPerosnjson){
            System.out.println(pers1);

        }


    }
    public static String FiletoString(InputStream inStr) throws IOException {
        StringBuilder builder = new StringBuilder();
        String filestr = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
        while ( (filestr = reader.readLine()) != null){
             builder.append(filestr);
        }

        return builder.toString();


    }
}
