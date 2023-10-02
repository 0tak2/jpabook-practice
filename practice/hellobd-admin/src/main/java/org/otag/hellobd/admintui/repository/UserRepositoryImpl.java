package org.otag.hellobd.admintui.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    EntityManager em;

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            user = em.createQuery("select u from Users u where u.username = :username and u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }

        return Optional.ofNullable(user);
    }

    @Override
    public void insert(User user) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        tx.commit();
    }

    @Override
    public List<User> selectList() {
        return em.createQuery("select u from Users u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        try {
            user = em.createQuery("select u from Users u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }

        return Optional.ofNullable(user);
    }
}
