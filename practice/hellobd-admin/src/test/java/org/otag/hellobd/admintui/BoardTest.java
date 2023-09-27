package org.otag.hellobd.admintui;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.otag.hellobd.admintui.entity.*;
import org.otag.hellobd.admintui.entity.id.ArticleLikeId;

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

    @Test
    public void 댓글저장() {
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

        // 댓글 저장
        Comment comment = Comment.builder()
                .author(user)
                .article(article)
                .content("테스트")
                .isHidden(false)
                .build();
        article.getComments().add(comment); // 양방향
        em.persist(article);

        // 댓글 조회
        Article foundArticle = em.find(Article.class, article.getId());
        Comment foundComment = null;
        for (Comment c : foundArticle.getComments()) {
            foundComment = c;
        }
        assertEquals(foundComment, comment);

        tx.commit();
        em.close();
        emf.close();
    }

    @Test
    public void 게시판에_관리자_추가() {
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

        // 게시판에 관리자 추가
        BoardAdmin boardAdmin = BoardAdmin.builder()
                .admin(user)
                .board(board)
                .build();
        user.getAdminBoards().add(boardAdmin); // 양방향
        em.persist(boardAdmin);

        // 조인 조회
        BoardAdmin foundBoardAdmin = em.createQuery(
                "SELECT ba FROM BoardAdmin ba JOIN ba.admin JOIN ba.board",
                BoardAdmin.class
        )
        .getSingleResult();

        System.out.println(foundBoardAdmin);

        assertEquals(foundBoardAdmin.getAdmin(), user);
        assertEquals(foundBoardAdmin.getBoard(), board);

        tx.commit();
        em.close();
        emf.close();
    }

    @Test
    public void 게시글좋아요() {
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

        // 댓글 저장
        Comment comment = Comment.builder()
                .author(user)
                .article(article)
                .content("테스트")
                .isHidden(false)
                .build();
        article.getComments().add(comment); // 양방향
        em.persist(article);

        // 게시글 좋아요
        ArticleLike articleLike = ArticleLike.builder()
                .article(article)
                .user(user)
                .build();
        em.persist(articleLike);

        // 테스트
        ArticleLike foundArticleLike = em.find(ArticleLike.class, new ArticleLikeId(article.getId(), user.getId()));

        assertEquals(foundArticleLike.getArticle(), article);
        assertEquals(foundArticleLike.getUser(), user);

        tx.commit();
        em.close();
        emf.close();
    }
}
