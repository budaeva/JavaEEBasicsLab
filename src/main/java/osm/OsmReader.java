package osm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

public abstract class OsmReader implements AutoCloseable {
    protected final OsmContainer osmContainer;

    protected final XMLStreamReader reader;

    protected Logger logger = LoggerFactory.getLogger(OsmReader.class);

    public OsmReader(InputStream is) throws XMLStreamException {
        osmContainer = new OsmContainer();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        reader = xmlInputFactory.createXMLStreamReader(is);
    }

    public abstract OsmContainer read() throws XMLStreamException, JAXBException;
    public OsmContainer getOsmContainer() {
        return osmContainer;
    }

    @Override
    public void close() {
        logger.info("OsmReader: close");
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) { // empty
            }
        }
    }

}
