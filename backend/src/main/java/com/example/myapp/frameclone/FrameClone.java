package com.example.myapp.frameclone;

import com.example.myapp.auth.entity.User;
import com.example.myapp.major.Major;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "frame_clones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameClone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_major_id")
    private Major sourceMajor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_major_id")
    private Major targetMajor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime clonedAt;

    @PrePersist
    protected void onCreate() {
        if (clonedAt == null) clonedAt = LocalDateTime.now();
    }
}


