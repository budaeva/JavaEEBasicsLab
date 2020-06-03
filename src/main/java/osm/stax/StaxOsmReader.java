package osm.stax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osm.OsmContainer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StaxOsmReader implements AutoCloseable {
    private final OsmContainer osmContainer;

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader reader;

    private Logger logger = LoggerFactory.getLogger(StaxOsmReader.class);

    public StaxOsmReader(InputStream is) throws XMLStreamException {
        logger.info("StaxOsmReader Constructor");
        osmContainer = new OsmContainer();
        reader = FACTORY.createXMLStreamReader(is);
    }

    public OsmContainer read() throws XMLStreamException {
        logger.info("StaxOsmReader read");
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT
                    && "node".equals(reader.getLocalName())) {
                String user = reader.getAttributeValue(null, "user");
                osmContainer.addUserChange(user);
                while (reader.hasNext()) {
                    int currentEvent = reader.next();
                    //to exit
                    if (currentEvent == XMLEvent.END_ELEMENT && "node".equals(reader.getLocalName())){
                        break;
                    }
                    if (currentEvent == XMLEvent.START_ELEMENT
                            && "tag".equals(reader.getLocalName())) {
                        String key = reader.getAttributeValue(null, "k");
                        osmContainer.addKeyCount(key);
                    }
                }
            }
        }
        return osmContainer;
    }





    public OsmContainer getOsmContainer() {
        return osmContainer;
    }

    @Override
    public void close() {
        logger.info("close");
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) { // empty
            }
        }
    }
}
