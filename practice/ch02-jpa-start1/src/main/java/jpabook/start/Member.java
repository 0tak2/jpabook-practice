package jpabook.start;

import javax.persistence.*;

@Entity // 이 클래스를 테이블과 매핑한다고 JPA에 알려줌. 이제부터 이 클래스는 엔티티클래스
@Table(name = "MEMBER") // 엔티티 클래스에 매핑할 테이블 정보를 알려줌. 생략시 클래스 이름(정확히는 엔티티 이름)을 테이블 이름으로 매핑
public class Member {
    // 필드
    @Id // PK 지정 -> 식별자 필드
    @Column(name = "ID") // 테이블의 ID 컬럼과 매핑
    private String id; // 아이디

    @Column(name = "NAME")
    private String username; // 이름

    // 매핑 정보가 필요 없음
    // 매핑 어노테이션 생략시 필드명을 컬럼명으로 매핑
    // 다만 대소문자를 구분하는 DBMS라면 AGE라는 컬럼명을 명시해줘야 함
    private Integer age; // 나이


    // Getter, Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
