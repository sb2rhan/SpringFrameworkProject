package org.step.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.entities.Course;
import org.step.repositories.CrudRepository;
import org.step.services.CrudService;

import java.util.List;

@Service
public class CourseServiceImpl implements CrudService<Course> {

    private final CrudRepository<Course> courseCrudRepository;

    @Autowired
    public CourseServiceImpl(@Qualifier("courseRepositoryImpl") CrudRepository<Course> courseCrudRepository) {
        this.courseCrudRepository = courseCrudRepository;
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseCrudRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Course find(Long id) {
        return courseCrudRepository.find(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseCrudRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseCrudRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        courseCrudRepository.deleteAll();
    }
}
