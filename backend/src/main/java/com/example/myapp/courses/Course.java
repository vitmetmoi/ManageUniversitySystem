package com.example.myapp.courses;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "credits")
    private Double credits;

    @Column(name = "credit_theory")
    private Double creditTheory;

    @Column(name = "pratice_theory")
    private Double praticeTheory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_elective_id")
    private Course courseElective;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_parallel_id")
    private Course courseParallel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_previous_id")
    private Course coursePrevious;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (creditTheory == null)
            creditTheory = 0.0;
        if (praticeTheory == null)
            praticeTheory = 0.0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
