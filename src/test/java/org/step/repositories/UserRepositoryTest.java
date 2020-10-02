package org.step.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.step.configuration.DBConfiguration;
import org.step.entities.User;
import org.step.entities.projections.UserProjection;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBConfiguration.class})
@ActiveProfiles("dev")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionTemplate transactionTemplate;

    @Before
    public void setup() {
        System.out.println("----------------------------SETUP----------------------------");
        if (transactionTemplate == null)
            transactionTemplate = new TransactionTemplate(transactionManager);
        Random random = new Random();
        transactionTemplate
                .execute(status -> {
                    Stream.generate(
                    () -> User.builder()
                            .username("person " + random.nextInt(20))
                            .age((int)Math.floor((Math.random()+1) * 16))
                            .password("test33223")
                            .build())
                            .limit(3)
                            .forEach(userRepository::save);
            return status;
        });
        System.out.println("----------------------------SETUP----------------------------");
    }

    @After
    public void clean() {
        System.out.println("----------------------------CLEAN----------------------------");
        transactionTemplate
                .execute(status -> {
                    userRepository.deleteAll();
                    return status;
                });
        System.out.println("----------------------------CLEAN----------------------------");
    }

    @Test
    public void findByProjection() {
        List<UserProjection> allUsersProjectionById = userRepository.findAllUsersProjection();

        Assert.assertNotNull(allUsersProjectionById);
        Assert.assertFalse(allUsersProjectionById.isEmpty());
        System.out.println("----------------------------OUTPUT----------------------------");
        allUsersProjectionById
                .forEach(u -> {
                    System.out.printf("%s %d y.o.%n",
                        u.getUsername(), u.getAge());
                    u.getMessages().forEach(System.out::println);
                });
        System.out.println("----------------------------OUTPUT----------------------------");
    }

    @Test
    public void findByEntityGraph() {
        Long id = userRepository.findAll().get(0).getId();
        Optional<User> userByIdEntityGraph = userRepository.findUserByIdEntityGraph(id);


        userByIdEntityGraph.ifPresentOrElse(System.out::println,
                () -> { throw new RuntimeException("User is empty"); });
    }

    @Test
    public void exampleMatcherTest() {
        // Так же попробуйте Example API

        User user = User.builder()
                .username("Clark")
                .password("kent23")
                .age(25)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("user", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnoreNullValues()
                .withIgnoreCase("id");

        Example<User> userExample = Example.of(user, matcher);

        List<User> users = userRepository.findAll(userExample);

        Assert.assertTrue(users.isEmpty());
    }
}
