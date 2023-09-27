package org.otag.hellobd.admintui.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.otag.hellobd.admintui.entity.id.CommentLikeId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@IdClass(CommentLikeId.class)
public class CommentLike {
    @Id
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createDt;
}
