import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osm.OsmContainer;
import osm.OsmReader;

import javax.xml.stream.XMLStreamException;
import java.io.*;

public class Application {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Application.class);
        logger.info("main");
        printHelloWorld(System.out);

        String path = parseArgs(args);
        if ("".equals(path)) {
            System.out.println("File name not specified");
            System.exit(0);
        }

        try (InputStream in = new BZip2CompressorInputStream(new FileInputStream(path))) {
            OsmContainer osmContainer = new OsmReader(in).read();
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
        logger.info("main end");
    }

    private static String parseArgs(String[] args) {
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
        LoggerFactory.getLogger(Application.class).info("printHelloWorld");
        out.println("Hello, World!");
    }
}