package org.otag.hellobd.admintui.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.otag.hellobd.admintui.entity.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "adminBoards")
@Entity(name = "Users")
@Table(indexes = @Index(columnList = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(length = 48, unique = true)
    private String username;

    @Column(length = 128)
    private String password;

    @Column(length = 48)
    private String name;

    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private RoleEnum role;

    @OneToMany(mappedBy = "admin")
    private final Set<BoardAdmin> adminBoards = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime create_dt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime update_dt;
}
