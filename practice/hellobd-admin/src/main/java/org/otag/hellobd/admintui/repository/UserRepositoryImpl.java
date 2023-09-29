package org.otag.hellobd.admintui.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    EntityManager em;

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        User user = em.createQuery("select u from Users u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return Optional.of(user);
    }
}
