package db;

import db.dao.NodeDao;
import db.dao.TagDao;
import db.entities.NodeEntity;
import db.entities.TagEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osm.jaxb.generated.Node;
import osm.jaxb.generated.Tag;

import java.util.ArrayList;

public class Service {
    private static Logger logger = LoggerFactory.getLogger(Service .class);
    private final NodeDao nodeDao;
    private final TagDao tagDao;
    private ArrayList<NodeEntity> currentNodes;
    private final int CURRENT_NODES_SIZE = 4096;

    public Service() {
        nodeDao = new NodeDao();
        tagDao = new TagDao();
        currentNodes = new ArrayList<>();
    }

    public void putWithSimpleStatement(Node node) {
//        logger.info("Service: putNodeWithQuery");
        NodeEntity nodeEntity = new NodeEntity(node);
        nodeDao.insertWithQuery(nodeEntity);
        for (Tag tag : node.getTag()) {
            tagDao.insertWithQuery(new TagEntity(tag, nodeEntity.getId()));
        }
    }

    public void putWithPreparedStatement(Node node) {
        NodeEntity nodeEntity = new NodeEntity(node);
        nodeDao.insertWithPrepared(nodeEntity);
        for (Tag tag : node.getTag()) {
            tagDao.insertWithPrepared(new TagEntity(tag, nodeEntity.getId()));
        }
    }

    public void putWithBatch(Node node) {
        NodeEntity nodeEntity = new NodeEntity(node);
        currentNodes.add(nodeEntity);
        if (currentNodes.size() == CURRENT_NODES_SIZE) {
            insertBatch();
            currentNodes.clear();
        }
    }

    private void insertBatch() {
        nodeDao.insertWithBatch(currentNodes);
        for (NodeEntity nodeEntity : currentNodes)
            tagDao.insertWithBatch(nodeEntity.getTags());
    }
}
