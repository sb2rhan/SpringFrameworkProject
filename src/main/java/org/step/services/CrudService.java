package org.step.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    T save(T t);

    T find(Long id);

    List<T> findAll();

    void delete(Long id);

    void deleteAll();
}
