package db.dao;

import db.DbUtils;
import db.entities.TagEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            setFieldsForInsert(preparedStatement, value);
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
                setFieldsForInsert(batch, tagEntity);
                batch.addBatch();
            }
            batch.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void setFieldsForInsert(PreparedStatement preparedStatement, TagEntity tagEntity) throws SQLException {
        preparedStatement.setLong(1, tagEntity.getNode_id());
        preparedStatement.setString(2, tagEntity.getKey());
        preparedStatement.setString(3, tagEntity.getValue());
    }


    @Override
    public TagEntity getById(long id) {
        String sql = " select * from " + TAGS_TABLE_NAME + " where node_id = ?;";
        try (PreparedStatement preparedStatement = DbUtils.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? new TagEntity(resultSet.getLong("node_id"), resultSet.getString("key"),
                    resultSet.getString("value")) : null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
