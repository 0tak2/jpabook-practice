package org.otag.hellobd.admintui.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(length = 48)
    private String username;

    @Column(length = 128)
    private String password;

    @Column(length = 48)
    private String name;

    @Column
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private RoleEnum role;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime create_dt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime update_dt;
}
