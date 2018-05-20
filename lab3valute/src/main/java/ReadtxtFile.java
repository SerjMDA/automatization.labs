import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
public class ReadtxtFile {
    private static final String SAMPLE_CSV_FILE_PATH = "valute.txt";

    public static void main(String[] args) throws IOException {
        Client client = Client.create();
        WebResource webResource;
        ClientResponse clientResponse;
        XStream xxStream = new XStream();

        xxStream.processAnnotations(ValuteBNM.class);
        xxStream.processAnnotations(ValuteBNMCurs.class);

        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {

                String data = csvRecord.get(2);
                System.out.println(data);
                webResource = client.resource("https://bnm.md/en/official_exchange_rates?get_xml=1&date="+data);
                clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
                if (clientResponse.getStatus()!=200){
                    throw new RuntimeException("Failed to connect! "+clientResponse.getStatus());
                }
                String output = clientResponse.getEntity(String.class);


                ValuteBNMCurs step= (ValuteBNMCurs)xxStream.fromXML(output);
                for(ValuteBNM random:step.getCur())
                    System.out.println(random);
                System.out.println("*********************************************************");
            }
        }


    }
}

