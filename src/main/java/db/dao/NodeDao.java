package db.dao;

import db.entities.Node;

import java.sql.Statement;
import java.util.List;

public class NodeDao implements Dao<Node> {

    @Override
    public void insertWithQuery(Node value) {
        Statement
    }

    @Override
    public void insertWithPrepared(Node value) {

    }

    @Override
    public void insertWithBatch(Node value) {

    }

    @Override
    public Node getById(long id) {
        return null;
    }

    @Override
    public List<Node> getAll() {
        return null;
    }

    @Override
    public void update(Node value) {

    }

    @Override
    public void delete(Node value) {

    }
}
