package org.step.repositories.impl;

import org.springframework.stereotype.Repository;
import org.step.entities.Course;
import org.step.repositories.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements CrudRepository<Course> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Course save(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public Course find(Long id) {
        return entityManager.createQuery("select c from Course c where c.id=:id", Course.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Course> findAll() {
        return entityManager.createQuery("select c from Course c", Course.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Course c where c.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Course c")
                .executeUpdate();
    }
}
