package db.dao;

import db.DbUtils;
import db.entities.TagEntity;
import osm.jaxb.generated.Tag;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TagDao implements Dao<TagEntity> {

    @Override
    public void insertWithQuery(TagEntity value) {
        try (Statement statement = DbUtils.getConnection().createStatement()){
            String sql = "insert into " + TAGS_TABLE_NAME +
                    " values(" +
                    value.getNode_id() + ", " +
                    "'" + checkString(value.getKey()) + "'" + ", " +
                    "'" + checkString(value.getValue()) + "'"
                    + ");";
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertWithPrepared(TagEntity value) {
        String preSql = "insert into " + TAGS_TABLE_NAME + " (node_id, key, value) values (?, ?, ?);";
        try (PreparedStatement preparedStatement = DbUtils.getConnection().prepareStatement(preSql)) {
            setFields(preparedStatement, value);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertWithBatch(List<TagEntity> values) {
        String preSql = "insert into " + TAGS_TABLE_NAME + " (node_id, key, value) values (?, ?, ?);";
        try (PreparedStatement batch = DbUtils.getConnection().prepareStatement(preSql)) {
            for (TagEntity tagEntity : values) {
                setFields(batch, tagEntity);
                batch.addBatch();
            }
            batch.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void setFields(PreparedStatement preparedStatement, TagEntity tagEntity) throws SQLException {
        preparedStatement.setLong(1, tagEntity.getNode_id());
        preparedStatement.setString(2, tagEntity.getKey());
        preparedStatement.setString(3, tagEntity.getValue());
    }


    @Override
    public TagEntity getById(long id) {
        return null;
    }

    @Override
    public List<TagEntity> getAll() {
        return null;
    }

    @Override
    public void update(TagEntity value) {

    }

    @Override
    public void delete(TagEntity value) {

    }
}
