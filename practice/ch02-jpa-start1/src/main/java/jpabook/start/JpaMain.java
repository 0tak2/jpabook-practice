package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabook");

        // 엔티티 매니저 팩토리로부터 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        // 엔티티 매니저로부터 트랜잭션 객체 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            login(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("DB 업무 중 문제가 발생했습니다.");
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    // 비즈니스 로직
    private static void login(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("순택");
        member.setAge(1);

        // 등록
        em.persist(member); // INSERT문 생성 및 실행

        // 수정
        member.setAge(8); // 엔티티의 필드를 수정만 하면 JPA는 변경 사항을 추적하여 UPDATE문 생성 및 실행

        // 단건 조회
        Member findMember = em.find(Member.class, id); // @Id로 매핑한 식별자 값으로 엔티티 한 개를 조회
        System.out.println("findMember=" + findMember.getUsername()
            + ", age=" + findMember.getAge());

        // 목록 조회 (JPQL)
        // JPQL: SQL을 축상화한 객체지향 쿼리 언어. 테이블이 아닌 엔티티 객체를 대상으로 질의한다.
        // 엔티티인 Member가 아니라 테이블명인 MEMBER로 작성하면 오류 발생.
        List<Member> members =
                em.createQuery("select m from Member m", Member.class)
                        .getResultList(); // JPQL로부터 적절한 SQL문을 작성하여 실행
        System.out.println("members.size=" + members.size());

        // 삭제
        em.remove(member); // DELETE문 생성 및 실행
    }
}
