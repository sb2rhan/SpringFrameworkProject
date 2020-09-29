package org.step.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
// do not write user
@Table(name = "users")
@NamedEntityGraph(
        name = User.USER_MESSAGE_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = User_.MESSAGES)
        }
        // subgraph - зависимости у нода
)
public class User {

    public static final String USER_MESSAGE_GRAPH = "User[messageList]";

    // GenerationType.TABLE - hibernate is responsible for generation ids by hibernate_sequence
    // GenerationType.IDENTITY - table must have auto increment property on id column
    // GenerationType.SEQUENCE - (@SequenceGenerator) - hibernate creates sequence as we declared
    // or it is uses our sequence which we have created
    // GenerationType.AUTO - allow hibernate decide what strategy to use (not recommended)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
//    @SequenceGenerator(allocationSize = 1, sequenceName = "user_seq", name = "user_gen")
//    @SequenceGenerator(name = "user_gen", allocationSize = 1, sequenceName = "user_seq", initialValue = 50)
    @Column(name = "id")
    private Long id;

    @Size(min = 5, max = 128, message = "The length of username should be at least 10 to 128 symbols")
    @Column(name = "username", nullable = false, insertable = true, updatable = true, length = 128)
    private String username;

//    @NotNull
    // @NotBlank - не пустая строка
    @NotBlank
    @Column(name = "password")
    private String password;

    @Min(value = 18, message = "You should be at least 18 years old")
    @Max(value = 140, message = "You too old, heaven is waiting for you")
    @Column(name = "age", precision = 2, scale = 0)
    private Integer age;

    /*
   CascadeType

    ALL - все вместе
    PERSIST - сохранение
    DETACH - убрать из persistence context
    MERGE - обновление (update)
    REFRESH - обновление данных из базы данных в persistence context
    REMOVE - *** используется крайне редко *** - каскадное удаление

    FetchType
    EAGER - жадный select
    LAZY - ленивый select (На проектах почти всегда)
     */
    @OneToOne(
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    private Profile profile;

    // mappedBy = имя объекта должно быть такое же, как и в самой сущности Message
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "user"
    )
//    @Fetch(FetchMode.SUBSELECT)
    private List<Message> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "userSet", fetch = FetchType.LAZY)
    private Set<Course> courseSet = new HashSet<>();

    public User() {
    }

    private User(Long id, String username, String password, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
        profile.setUser(this);
    }

    public void addMessage(Message message) {
//        if (this.messageList == null) {
//            this.messageList = new ArrayList<>();
//        }
        this.messages.add(message);
        message.setUser(this);
    }

    public void addCourse(Course course) {
        this.courseSet.add(course);
        course.getUserSet().add(this);
    }

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messageList) {
        this.messages = messageList;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private Integer age;

        private UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(id, username, password, age);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
