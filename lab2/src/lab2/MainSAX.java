package lab2;
// Создание XML SAX подход
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class MainSAX {
 public static void main(String[]args) throws ParserConfigurationException,IOException,SAXException,XMLStreamException,TransformerException{
	XMLOutputFactory factory=XMLOutputFactory.newFactory();
	XMLStreamWriter writer=factory.createXMLStreamWriter(new FileOutputStream("fileSAX.xml"));
	writer.writeStartDocument();
	writer.writeStartElement("note");
	writer.writeStartElement("to");
	writer.writeCharacters("Виктор");
	writer.writeEndElement();
	writer.writeStartElement("from");
	writer.writeCharacters("Михаил");
	writer.writeEndElement();
	writer.writeStartElement("heading");
	writer.writeCharacters("Напоминание");
	writer.writeEndElement();
	writer.writeStartElement("body");
	writer.writeCharacters("Позвони мне завтра!");
	writer.writeEndElement();
	writer.writeEndDocument();
 }
 
}
