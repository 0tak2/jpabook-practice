package org.otag.hellobd.admintui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.otag.hellobd.admintui.entity.*;
import org.otag.hellobd.admintui.entity.enums.ReportStatusEnum;
import org.otag.hellobd.admintui.entity.enums.ReportTargetEnum;
import org.otag.hellobd.admintui.entity.enums.ReportTypeEnum;
import org.otag.hellobd.admintui.entity.id.ArticleLikeId;
import org.otag.hellobd.admintui.entity.id.CommentLikeId;

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

    @Test
    public void 게시글_댓글_신고() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hellobd-admintui");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // 유저 저장
        User reporter = User.builder()
                .username("신고자")
                .isActive(true)
                .build();

        User reported = User.builder()
                .username("피신고자")
                .isActive(true)
                .build();

        em.persist(reporter);
        em.persist(reported);

        // 게시판 저장
        Board board = Board.builder()
                .name("테스트")
                .description("테스트")
                .build();
        em.persist(board);

        // 게시글 저장
        Article article = Article.builder()
                .author(reported)
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
                .author(reported)
                .article(article)
                .content("테스트")
                .isHidden(false)
                .build();
        article.getComments().add(comment); // 양방향
        em.persist(comment);

        // 게시글 신고
        Report articleReport = Report.builder()
                .reporter(reporter)
                .targetType(ReportTargetEnum.ARTICLE)
                .targetArticle(article)
                .description("게시글 신고 테스트 - 유저 측 설명")
                .reportType(ReportTypeEnum.HARASSMENT)
                .status(ReportStatusEnum.PENDING)
                .result("게시글 신고 테스트 - 운영 측 통보")
                .build();

        em.persist(articleReport);

        // 댓글 신고
        Report commentReport = Report.builder()
                .reporter(reporter)
                .targetType(ReportTargetEnum.COMMENT)
                .targetComment(comment)
                .description("댓글 신고 테스트 - 유저 측 설명")
                .reportType(ReportTypeEnum.HATE)
                .status(ReportStatusEnum.RESOLVED)
                .result("댓글 신고 테스트 - 운영 측 통보")
                .build();

        em.persist(commentReport);

        // 유저 페널티 추가
        UserPenalty userPenalty = UserPenalty.builder()
                .user(reported)
                .increment(1)
                .count(1)
                .description("테스트")
                .report(commentReport)
                .build();
        em.persist(userPenalty);

        // 테스트
        Report foundArticleReport = em.find(Report.class, articleReport.getId());
        assertEquals(foundArticleReport.getDescription(), "게시글 신고 테스트 - 유저 측 설명");
        assertEquals(foundArticleReport.getResult(), "게시글 신고 테스트 - 운영 측 통보");

        Report foundCommentReport = em.find(Report.class, commentReport.getId());
        assertEquals(foundCommentReport.getTargetComment(), comment);
        assertEquals(foundCommentReport.getReporter(), reporter);

        tx.commit();
        em.close();
        emf.close();
    }
}
