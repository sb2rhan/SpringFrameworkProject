package org.step.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.step.entities.User;
import org.step.entities.projections.UserProjection;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
/*Создаем репозиторий Spring Data к ней и далее*/
public interface UserRepository extends JpaRepository<User, Long> {

    // Используем создание query из кода;
    List<User> findAllByUsername(String username);

    // Используем создание query из аннотации Query;
    @Query("select u from User u where u.age < 18")
    List<User> findUnderageUsers();

    // Используем insert
    @Query(nativeQuery = true, value = "INSERT INTO users(username, password, age) VALUES(?1, ?2, ?3)")
    @Modifying(flushAutomatically = true)
    User saveNewUser(String username, String password, int age);

    // Используем update
    @Query("update User u set u.username=?1 where u.id=?2")
    @Modifying(flushAutomatically = true)
    // Используем QueryHint
    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FLUSH_MODE, value = "AUTO")
    })
    void updateUsername(String newUsername, Long id);

    // Используем delete
    @Modifying(clearAutomatically = true)
    void deleteUserByIdIsIn(List<Long> idsToDelete);

    @Modifying(clearAutomatically = true)
    void deleteUserByUsernameIsLike(String username);

    // Используем EntityGraph
    @EntityGraph(value = User.USER_MESSAGE_GRAPH, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select u from User u where u.id=?1")
    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")
    })
    Optional<User> findUserByIdEntityGraph(Long id);

    // Один из видов Projection
    @Query("select u from User u")
    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true")
    })
    List<UserProjection> findAllUsersProjection();
}

