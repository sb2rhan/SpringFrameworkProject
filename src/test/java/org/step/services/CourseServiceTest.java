package org.step.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.step.configuration.DBConfiguration;
import org.step.entities.Course;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConfiguration.class})
@ActiveProfiles("dev")
public class CourseServiceTest {
    @Autowired
    @Qualifier("courseServiceImpl")
    private CrudService<Course> courseCrudService;

    private Course course;

    @Before
    public void setup() {
        course = Course.builder()
                .topic("Spring Boot")
                .courseDescription("We will learn about Spring Boot")
                .build();
        courseCrudService.save(course);
    }

    @After
    public void clean() {
        courseCrudService.deleteAll();
    }

    @Test
    public void saveNewCourse() {
        Course newCourse = Course.builder()
                .build();
        Course check = courseCrudService.save(newCourse);

        Assert.assertEquals(newCourse.getId(), check.getId());
    }

    @Test
    public void deleteCourse() {
        courseCrudService.delete(course.getId());
    }

    @Test
    public void findCourse() {
        final Course course = courseCrudService.find(this.course.getId());

        Assert.assertNotNull(course);
        System.out.println("Returned course in findCourse test: " + course.toString());
        Assert.assertEquals(course.getId(), this.course.getId());
    }

    @Test
    public void findAllCourses() {
        List<Course> courses = courseCrudService.findAll();

        Assert.assertFalse(courses.isEmpty());
    }
}
