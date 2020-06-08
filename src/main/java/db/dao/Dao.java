package db.dao;

import java.util.List;

public interface Dao<T> {
    String NODES_TABLE_NAME = "nodes";
    String TAGS_TABLE_NAME = "tags";
    void insertWithQuery(T value);
    void insertWithPrepared(T value);
    void insertWithBatch(List<T> values);
    T getById(long id);
    List<T> getAll();
    void update(T value);
    void delete(T value);

    default String checkString(String src) {
        if (src.contains("'")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!1   " + src + "       !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return src.replace("'", "\'");
        }
        return src;
    }

}
