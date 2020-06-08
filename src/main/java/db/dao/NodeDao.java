package db.dao;

import db.DbUtils;
import db.entities.NodeEntity;
import db.entities.TagEntity;
import osm.jaxb.generated.Node;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NodeDao implements Dao<NodeEntity> {

    @Override
    public void insertWithQuery(NodeEntity value) {
        String sql = "insert into " + NODES_TABLE_NAME +
                " values(" +
                value.getId() + ", " +
                value.getLon() + ", " +
                value.getLat() + ", " +
                "'" + checkString(value.getUser()) + "'"
                + ");";
        try (Statement statement = DbUtils.getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertWithPrepared(NodeEntity value) {
        String preSql = "insert into " + NODES_TABLE_NAME + " (id, lon, lat, username) values (?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = DbUtils.getConnection().prepareStatement(preSql)) {
            setFields(preparedStatement, value);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertWithBatch(List<NodeEntity> values) {
        String preSql = "insert into " + NODES_TABLE_NAME + " (id, lon, lat, username) values (?, ?, ?, ?);";
        try (PreparedStatement batch = DbUtils.getConnection().prepareStatement(preSql)) {
            for (NodeEntity nodeEntity : values) {
                setFields(batch, nodeEntity);
                batch.addBatch();
            }
            batch.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void setFields(PreparedStatement preparedStatement, NodeEntity nodeEntity) throws SQLException {
        preparedStatement.setLong(1, nodeEntity.getId());
        preparedStatement.setDouble(2, nodeEntity.getLon());
        preparedStatement.setDouble(3, nodeEntity.getLat());
        preparedStatement.setString(4, checkString(nodeEntity.getUser()));
    }

    @Override
    public NodeEntity getById(long id) {
        return null;
    }

    @Override
    public List<NodeEntity> getAll() {
        return null;
    }

    @Override
    public void update(NodeEntity value) {

    }

    @Override
    public void delete(NodeEntity value) {

    }


}
