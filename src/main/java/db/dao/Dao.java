package db.dao;

import java.util.List;

public interface Dao<T> {
    String NODES_TABLE_NAME = "nodes";
    String TAGS_TABLE_NAME = "tags";
    void insertWithQuery(T value);
    void insertWithPrepared(T value);
    void insertWithBatch(List<T> values);
    T getById(long id);

    default String checkString(String src) {
        if (src.contains("'")) {
            return src.replace("'", "\'");
        }
        return src;
    }

}
