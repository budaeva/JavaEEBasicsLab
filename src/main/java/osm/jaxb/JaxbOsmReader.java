package osm.jaxb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osm.OsmContainer;
import osm.jaxb.generated.Node;
import osm.jaxb.generated.Tag;
import osm.stax.StaxOsmReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JaxbOsmReader {
    private final OsmContainer osmContainer;

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private final XMLStreamReader reader;

    private Logger logger = LoggerFactory.getLogger(StaxOsmReader.class);

    public JaxbOsmReader(InputStream is) throws XMLStreamException {
        logger.info("JaxbOsmReader Constructor");
        osmContainer = new OsmContainer();
        reader = FACTORY.createXMLStreamReader(is);
    }
    public OsmContainer read() throws XMLStreamException, JAXBException {
        logger.info("StaxOsmReader read");
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT
                    && "node".equals(reader.getLocalName())) {
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Node node = (Node) unmarshaller.unmarshal(reader);
                osmContainer.addUserChange(node.getUser());
                List<Tag> tags = node.getTag();
                tags.forEach(t -> osmContainer.addKeyCount(t.getK()));
            }
        }
        return osmContainer;
    }

}
