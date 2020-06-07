package db.dao;

import java.util.List;

public interface Dao<T> {
    void insertWithQuery(T value);
    void insertWithPrepared(T value);
    void insertWithBatch(T value);
    T getById(long id);
    List<T> getAll();
    void update(T value);
    void delete(T value);
}
