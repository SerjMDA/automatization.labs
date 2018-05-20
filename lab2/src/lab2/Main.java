package lab2;

//Создание XML DOM подход
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

public class Main {
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException,
			XMLStreamException, TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = (Document) builder.newDocument();
		Element note = document.createElement("note");
		Element to = document.createElement("to");
		Element from = document.createElement("from");
		Element heading = document.createElement("heading");
		Element body = document.createElement("body");

		Text text = document.createTextNode("Виктор");
		Text text1 = document.createTextNode("Михаил");
		Text text2 = document.createTextNode("Напоминание");
		Text text3 = document.createTextNode("Позвони мне завтра!");

		document.appendChild(note);
		note.appendChild(to);
		to.appendChild(text);
		note.appendChild(from);
		from.appendChild(text1);
		note.appendChild(heading);
		heading.appendChild(text2);
		note.appendChild(body);
		body.appendChild(text3);

		// сохранение xml файла

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");// чтобы не было все в одну строчку
		t.transform(new DOMSource(document), new StreamResult(new FileOutputStream("file.xml")));

	}

}
