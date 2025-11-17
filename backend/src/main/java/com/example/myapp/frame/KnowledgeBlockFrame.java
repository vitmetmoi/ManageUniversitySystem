package com.example.myapp.frame;

import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "knowledgeblock_frame")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBlockFrame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledgeblock_id", nullable = false)
    private KnowledgeBlock knowledgeBlock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id", nullable = false)
    private Frame frame;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "elective", nullable = false)
    private Boolean elective;

    @Column(name = "minimum_credits", precision = 5, scale = 2)
    private BigDecimal minimumCredits;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (isActive == null) isActive = true;
        if (elective == null) elective = false;
        if (minimumCredits == null) minimumCredits = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}