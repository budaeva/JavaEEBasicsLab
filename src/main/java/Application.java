import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osm.OsmContainer;
import osm.stax.StaxOsmReader;

import javax.xml.stream.XMLStreamException;
import java.io.*;

public class Application {
    private static Logger logger;
    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Application.class);
        logger.info("Application main");
        printHelloWorld(System.out);
//        osm.jaxb.JaxbOsmReader jaxbOsmReader = new JaxbOsmReader();
//        jaxbOsmReader.getContext();

        String path = parseArgs(args);
        if ("".equals(path)) {
            System.exit(0);
        }

        try (InputStream in = new BZip2CompressorInputStream(new FileInputStream(path))) {
            OsmContainer osmContainer = new StaxOsmReader(in).read();
            osmContainer.getUserChangesSortedByValue().forEach(elem ->
                    System.out.println(elem.getKey() + " changed " +
                            elem.getValue() + " times"));
            osmContainer.getKeysCount().forEach((key, tagsCount) ->
                    System.out.println(key + " tagged " + tagsCount + " times"));
        } catch (FileNotFoundException e) {
            System.out.println("Argument error: cannot find file!");
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static String parseArgs(String[] args) {
        logger.info("Application parseArgs");
        if (args.length < 1) {
            System.out.println("File name not specified");
            return "";
        }
        Options options = new Options();
        options.addOption("p", true, "path to OSM file");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            if (cmd.hasOption("p"))
                return cmd.getOptionValue("p");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void printHelloWorld(PrintStream out) {
        logger.info("printHelloWorld");
        System.out.println("Hello, World!");
    }
}