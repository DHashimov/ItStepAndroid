package demoapp.itsteps.com.db.adapters;

import java.util.List;

public interface DatabaseAdapter<T> {

    T findById(long id);

    List<T> findAll(String sortOrder);

    void insert(T model);

    void update(T model);

    void delete(long id);
}
