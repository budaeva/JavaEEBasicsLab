import db.DbUtils;
import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osm.OsmContainer;
import osm.jaxb.JaxbOsmProcessor;
import osm.stax.StaxOsmReader;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;

public class Application {
    private static Logger logger;
    private static String path = "";
    private static Boolean stax;


    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Application.class);
        logger.info("Application: main");
        printHelloWorld(System.out);

        parseArgs(args);
        loadDB();
        if ("".equals(path) || stax == null)
            System.exit(0);

        try (InputStream in = new BZip2CompressorInputStream(new FileInputStream(path))) {
            OsmContainer osmContainer;
            if (stax)
                osmContainer = new StaxOsmReader(in).read();
            else {
                osmContainer = new JaxbOsmProcessor(in).read();
            }
            DbUtils.closeConnection();

            osmContainer.getUserChangesSortedByValue().forEach(elem ->
                    System.out.println(elem.getKey() + " changed " +
                            elem.getValue() + " times"));
            osmContainer.getKeysCount().forEach((key, tagsCount) ->
                    System.out.println(key + " tagged " + tagsCount + " times"));
        } catch (FileNotFoundException e) {
            logger.info("Argument error: cannot find file!");
            logger.error(e.getMessage());
        } catch (IOException | XMLStreamException | JAXBException e) {
            logger.error(e.getMessage());
        }
    }


    private static void loadDB() {
        logger.info("Application: loadToDB");
        DbUtils.connect();
        DbUtils.initDB();
    }

    private static void parseArgs(String[] args) {
        logger.info("Application: parseArgs");
        if (args.length < 2) {
            String message = "Wrong amount of arguments";
            System.out.println(message);
            logger.info(message);
            return;
        }
        Options options = new Options();
        Option path = new Option("p", true, "path to OSM file");
        Option stax = new Option("stax", false, "unmarshall with stax");
        Option jaxb = new Option("jaxb", false, "unmarshall with jaxb");
        options.addOption(path);
        options.addOption(stax);
        options.addOption(jaxb);
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            if (cmd.hasOption(path.getOpt()))
                Application.path = cmd.getOptionValue(path.getOpt());
            if (cmd.hasOption(stax.getOpt()) && !cmd.hasOption(jaxb.getOpt()))
                Application.stax = true;
            else if (!cmd.hasOption(stax.getOpt()) && cmd.hasOption(jaxb.getOpt()))
                Application.stax = false;
            else {
                System.out.println("stax xor jaxb");
                Application.stax = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void printHelloWorld(PrintStream out) {
        logger.info("Application: printHelloWorld");
        System.out.println("Hello, World!");
    }
}