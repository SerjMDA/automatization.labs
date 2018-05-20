package lab2;
 //Parse xml breakfastmenuSAX.xml SAX
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class ParseBreakfastMenuSAX {
 
    private static final String FILENAME = "breakfastmenuSAX.xml";
 
    public static void main(String[] args) {
 
        SAXParser saxParser;
        try {
            saxParser = SAXParserFactory.newInstance().newSAXParser();
 
            DefaultHandler handler = new DefaultHandler() {
 
                boolean bname = false;
                boolean bprice = false;
                boolean bdescription = false;
                boolean bcalories = false;
 
                @Override
                public void startElement(String price, String description,
                        String name, Attributes attributes)
                        throws SAXException {
 
                    System.out.println("Начало элемента: " + name);
                    if (name.equalsIgnoreCase("Name")) {
                        bname = true;
                    }
                    if (name.equalsIgnoreCase("Price")) {
                        bprice = true;
                    }
                    if (name.equalsIgnoreCase("Description")) {
                        bdescription = true;
                    }
                    if (name.equalsIgnoreCase("Calories")) {
                        bcalories = true;
                    }
                   
                }
 
                @Override
                public void endElement(String from, String heading,
                        String to) throws SAXException {
                    System.out.println("Конец элемента: " + to);
                }
 
                @Override
                public void characters(char ch[], int start,
                        int length) throws SAXException {
                    if (bname) {
                        System.out.println("Name: "
                                + new String(ch, start, length));
                        bname = false;
                    }
                    if (bprice) {
                        System.out.println("Price: "
                                + new String(ch, start, length));
                        bprice = false;
                    }
                    if (bdescription) {
                        System.out.println("Description: "
                                + new String(ch, start, length));
                        bdescription = false;
                    }
                    if (bcalories) {
                        System.out.println("Calories: "
                                + new String(ch, start, length));
                        bcalories = false;
                    }
 
                }
            };
 
            saxParser.parse(System.getProperty("user.dir") + File.separator
                    + FILENAME, handler);
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            Logger.getLogger(ParseMainSAX.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}

