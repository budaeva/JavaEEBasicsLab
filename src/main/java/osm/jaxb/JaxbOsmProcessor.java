package osm.jaxb;

import db.Service;
import osm.OsmContainer;
import osm.OsmReader;
import osm.jaxb.generated.Node;
import osm.jaxb.generated.Tag;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.List;

public class JaxbOsmProcessor extends OsmReader {
    private final Service service;
    public JaxbOsmProcessor(InputStream is) throws XMLStreamException {
        super(is);
        logger.info("JaxbOsmReader: Constructor");
        service = new Service();
    }
    public OsmContainer read() throws XMLStreamException, JAXBException {
        logger.info("JaxbOsmReader: read");
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.START_ELEMENT
                    && "node".equals(reader.getLocalName())) {
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Node node = (Node) unmarshaller.unmarshal(reader);
                workNode(node);
            }
        }
        return osmContainer;
    }

    private void workNode(Node node) {
        osmContainer.addUserChange(node.getUser());
        List<Tag> tags = node.getTag();
        tags.forEach(t -> osmContainer.addKeyCount(t.getK()));
        service.putWithBatch(node);
    }

}
