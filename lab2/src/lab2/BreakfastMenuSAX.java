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

public class BreakfastMenuSAX {
 public static void main(String[]args) throws ParserConfigurationException,IOException,SAXException,XMLStreamException,TransformerException{
	XMLOutputFactory factory=XMLOutputFactory.newFactory();
	XMLStreamWriter writer=factory.createXMLStreamWriter(new FileOutputStream("breakfastmenuSAX.xml"));
	writer.writeStartDocument();
	writer.writeStartElement("food");
	writer.writeStartElement("name");
	writer.writeCharacters("Домашний завтрак");
	writer.writeEndElement();
	writer.writeStartElement("price");
	writer.writeCharacters("7$");
	writer.writeEndElement();
	writer.writeStartElement("description");
	writer.writeCharacters("Пара яиц, бекон или колбаса, тост, и наши всегда популярные картофельные оладьи");
	writer.writeEndElement();
	writer.writeStartElement("calories");
	writer.writeCharacters("950");
	writer.writeEndElement();
	writer.writeEndDocument();
 }
 
}

