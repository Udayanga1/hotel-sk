package repository;

import java.util.List;

public interface CrudRepository <T,ID> extends SuperDao{
    boolean save(T entity);
    boolean update(ID id,T entity);
    T search(T entity);
    boolean delete(ID id);
    List<T> getAll();

}
