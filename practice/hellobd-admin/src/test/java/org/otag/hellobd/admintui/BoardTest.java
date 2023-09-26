package org.otag.hellobd.admintui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.otag.hellobd.admintui.entity.Article;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    public void 게시글저장() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hellobd-admintui");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // 유저 저장
        User user = User.builder()
                .username("테스트")
                .isActive(true)
                .build();
        em.persist(user);

        // 게시판 저장
        Board board = Board.builder()
                        .name("테스트")
                        .description("테스트")
                        .build();
        em.persist(board);

        // 게시글 저장
        Article article = Article.builder()
                        .author(user)
                        .board(board)
                        .title("테스트")
                        .content("테스트")
                        .isHidden(false)
                        .viewCount(0L)
                        .build();
        board.getArticles().add(article); // 양방향
        em.persist(article);

        // 게시글 조회
        assertEquals(em.find(Article.class, article.getId()), article);
        assertEquals(em.find(User.class, article.getAuthor().getId()), user);
        assertEquals(em.find(Board.class, article.getBoard().getId()), board);

        tx.commit();
        em.close();
        emf.close();
    }
}
