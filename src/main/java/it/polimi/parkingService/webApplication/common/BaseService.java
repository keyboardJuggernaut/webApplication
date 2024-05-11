package it.polimi.parkingService.webApplication.common;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();

    T findById(long id);

    T save(T entity);

    void deleteById(long id);
}
