package lab2;

//Создание XML  файла DOM подход
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class BreakfastMenu {
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException,
			XMLStreamException, TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = (Document) builder.newDocument();
		Element food = document.createElement("food");
		Element name = document.createElement("name");
		Element price = document.createElement("price");
		Element description = document.createElement("description");
		Element calories = document.createElement("calories");

		Text text = document.createTextNode("Домашний завтрак");
		Text text1 = document.createTextNode("7$");
		Text text2 = document
				.createTextNode("Пара яиц, бекон или колбаса, тост, и наши всегда популярные картофельные оладьи");
		Text text3 = document.createTextNode("950");

		document.appendChild(food);
		food.appendChild(name);
		name.appendChild(text);
		food.appendChild(price);
		price.appendChild(text1);
		food.appendChild(description);
		description.appendChild(text2);
		food.appendChild(calories);
		calories.appendChild(text3);

		// сохранение xml файла

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");// чтобы не было все в одну строчку
		t.transform(new DOMSource(document), new StreamResult(new FileOutputStream("breakfastmenu.xml")));

	}

}
