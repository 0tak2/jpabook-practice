package org.otag.hellobd.admintui.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.otag.hellobd.admintui.entity.enums.ReportStatusEnum;
import org.otag.hellobd.admintui.entity.enums.ReportTargetEnum;
import org.otag.hellobd.admintui.entity.enums.ReportTypeEnum;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_user_id")
    private User reporter;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ReportTargetEnum targetType;

    @ManyToOne
    @JoinColumn(name = "target_article_id", nullable = true)
    private Article targetArticle;

    @ManyToOne
    @JoinColumn(name = "target_comment_id", nullable = true)
    private Comment targetComment;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ReportTypeEnum reportType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ReportStatusEnum status;

    @Column(length = 2000)
    private String result;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createDt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime updateDt;
}
