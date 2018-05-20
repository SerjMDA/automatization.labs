package lab2;
 //Parse xml file.xml SAX
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
 
public class ParseMainSAX {
 
    private static final String FILENAME = "fileSAX.xml";
 
    public static void main(String[] args) {
 
        SAXParser saxParser;
        try {
            saxParser = SAXParserFactory.newInstance().newSAXParser();
 
            DefaultHandler handler = new DefaultHandler() {
 
                boolean bto = false;
                boolean bfrom = false;
                boolean bheading = false;
                boolean bbody = false;
 
                @Override
                public void startElement(String from, String heading,
                        String to, Attributes attributes)
                        throws SAXException {
 
                    System.out.println("Начало элемента: " + to);
                    if (to.equalsIgnoreCase("To")) {
                        bto = true;
                    }
                    if (to.equalsIgnoreCase("From")) {
                        bfrom = true;
                    }
                    if (to.equalsIgnoreCase("Heading")) {
                        bheading = true;
                    }
                    if (to.equalsIgnoreCase("Body")) {
                        bbody = true;
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
                    if (bto) {
                        System.out.println("To: "
                                + new String(ch, start, length));
                        bto = false;
                    }
                    if (bfrom) {
                        System.out.println("From: "
                                + new String(ch, start, length));
                        bfrom = false;
                    }
                    if (bheading) {
                        System.out.println("Heading: "
                                + new String(ch, start, length));
                        bheading = false;
                    }
                    if (bbody) {
                        System.out.println("Body: "
                                + new String(ch, start, length));
                        bbody = false;
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

