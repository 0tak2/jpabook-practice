package org.otag.hellobd.admintui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.otag.hellobd.admintui.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @Test
    public void user저장테스트() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hellobd-admintui");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        User user = User.builder()
                .username("테스트")
                .isActive(true)
                .build();
        em.persist(user);

        assertEquals(em.find(User.class, user.getId()), user);

        tx.commit();
        em.close();
        emf.close();
    }
}
