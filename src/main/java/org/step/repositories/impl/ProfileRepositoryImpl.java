package org.step.repositories.impl;

import org.springframework.stereotype.Repository;
import org.step.entities.Profile;
import org.step.repositories.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProfileRepositoryImpl implements CrudRepository<Profile> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Profile save(Profile profile) {
        entityManager.persist(profile);
        return profile;
    }

    @Override
    public Profile find(Long id) {
        return entityManager.createQuery("select p from Profile p where p.id=:id", Profile.class)
                .getSingleResult();
    }

    @Override
    public List<Profile> findAll() {
        return entityManager.createQuery("select p from Profile p", Profile.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Profile p where p.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Profile p")
                .executeUpdate();
    }
}
