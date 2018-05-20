import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreatetxtFile {


    private static final String SAMPLE_CSV_FILE = "valute.txt";

    public static void main(String[] args) throws IOException {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        ) {
            csvPrinter.printRecord("1", "Mihail Dovbenco", "19.02.2018");
            csvPrinter.printRecord("2", "Mihail Dovbenco", "12.01.1999");
            csvPrinter.printRecord("3", "Mihail Dovbenco", "03.01.1995");
            csvPrinter.printRecord("4", "Mihail Dovbenco", "10.03.2007");
            csvPrinter.printRecord("5", "Mihail Dovbenco", "31.12.2014");


            csvPrinter.flush();
        }
    }
}

