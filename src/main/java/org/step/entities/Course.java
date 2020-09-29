package org.step.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "course_description")
    private String courseDescription;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_course",
            // joinColumns - для сущности, в которой пишем данный код
            joinColumns = @JoinColumn(name = "course_id"),
            // inverseJoinColumns - для сущности, на другой стороне
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userSet = new HashSet<>();

//    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
//    private Set<CourseRatingNewEntity> courseRatingNewEntities = new HashSet<>();

    public Course() {
    }

    private Course(Long id, String topic, String courseDescription) {
        this.id = id;
        this.topic = topic;
        this.courseDescription = courseDescription;
    }

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public static class CourseBuilder {
        private Long id;
        private String topic, courseDescription;

        private CourseBuilder() {
        }

        public CourseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseBuilder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public CourseBuilder courseDescription(String courseDescription) {
            this.courseDescription = courseDescription;
            return this;
        }

        public Course build() {
            return new Course(id, topic, courseDescription);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }
}
