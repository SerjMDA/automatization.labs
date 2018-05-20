package lab2;
//Parse xml file.xml DOM

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parsefilexml {
	private static final String FILENAME = "file.xml";

	public static void main(String[] args) {
		try {

			// Строим объектную модель исходного XML файла
			final File xmlFile = new File(System.getProperty("user.dir") + File.separator + FILENAME);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);

			System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());
			System.out.println("============================");

			// Получаем все узлы с именем "note"
			NodeList nodeList = doc.getElementsByTagName("note");

			for (int i = 0; i < nodeList.getLength(); i++) {
				// Выводим информацию по каждому из найденных элементов
				Node node = nodeList.item(i);
				System.out.println();
				System.out.println("Текущий элемент: " + node.getNodeName());
				if (Node.ELEMENT_NODE == node.getNodeType()) {
					Element element = (Element) node;

					System.out.println("Отправил: " + element.getElementsByTagName("to").item(0).getTextContent());
					System.out.println("Получил: " + element.getElementsByTagName("from").item(0).getTextContent());
					System.out.println("Что: " + element.getElementsByTagName("heading").item(0).getTextContent());
					System.out.println("Содержание: " + element.getElementsByTagName("body").item(0).getTextContent());
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			Logger.getLogger(Parsefilexml.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
