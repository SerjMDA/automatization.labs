package n3;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerDeserXML {

	public static void main(String[] args) throws IOException {

		XStream xStream = new XStream();
		// Create XML file
		FileWriter writer = new FileWriter("fileserdeser.xml");
		FileReader reader = new FileReader("fileserdeser.xml");
		

		// Creating firs test object
		Initials initials1 = new Initials("Danny", "Boy", "Worsnop");
		contactInfo contactInfo1 = new contactInfo("Dann1@gmail.com", "37360453542");

		Person person1 = new Person(initials1, 29, "Russian", contactInfo1,
				new ArrayList<String>(Arrays.asList("Phrase1", "Phrase2", "Phrase3")));

		// Creating second test object
		Initials initials2 = new Initials("Jonh", "Petrucio", "Frankovski");
		contactInfo contactInfo2 = new contactInfo("jonhpetrucio@gmail.com", "37369435627");

		Person person2 = new Person(initials2, 44, "Romanian", contactInfo2,
				new ArrayList<String>(Arrays.asList("Phrase4", "Phrase5", "Phrase6")));

		Annotations.configureAliases(xStream, Person.class);

		List<Person> personList = new ArrayList<>();
		personList.add(person1);
		personList.add(person2);
		String PersonsAsList = xStream.toXML(personList);
		writer.write(PersonsAsList);

		writer.flush();
		writer.close();
		System.out.println("File created successful");
		System.out.println("=====================================================");

		// from XML
		List<Person> PersonAsList2 = (List<Person>) xStream.fromXML(reader);
		System.out.println(PersonAsList2);

	}
}
