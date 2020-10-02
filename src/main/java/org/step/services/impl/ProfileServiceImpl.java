package org.step.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.entities.Profile;
import org.step.repositories.CrudRepository;
import org.step.services.CrudService;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements CrudService<Profile> {

    private final CrudRepository<Profile> profileCrudRepository;

    @Autowired
    public ProfileServiceImpl(@Qualifier("profileRepositoryImpl") CrudRepository<Profile> profileCrudRepository) {
        this.profileCrudRepository = profileCrudRepository;
    }

    @Override
    @Transactional
    public Profile save(Profile profile) {
        return profileCrudRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public Profile find(Long id) {
        return profileCrudRepository.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileCrudRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        profileCrudRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        profileCrudRepository.deleteAll();
    }
}
