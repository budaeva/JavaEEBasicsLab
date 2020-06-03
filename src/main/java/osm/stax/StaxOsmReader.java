package osm.stax;

import osm.OsmContainer;
import osm.OsmReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StaxOsmReader extends OsmReader {
    public StaxOsmReader(InputStream is) throws XMLStreamException {
        super(is);
        logger.info("StaxOsmReader Constructor");
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
}
