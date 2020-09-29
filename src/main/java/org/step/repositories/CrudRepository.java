package org.step.repositories;

import java.util.List;

public interface CrudRepository<T> {

    T save(T t);

    T find(Long id);

    List<T> findAll();

    void delete(Long id);

    void deleteAll();
}
