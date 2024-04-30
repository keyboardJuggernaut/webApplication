package it.polimi.parkingService.webApplication.utils;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();

    T findById(int id);

    T save(T entity);

    void deleteById(int id);
}
