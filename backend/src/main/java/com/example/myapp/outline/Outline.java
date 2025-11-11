package com.example.myapp.outline;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import com.example.myapp.courses.Course;

@Entity
@Getter
@Setter
public class Outline { 
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column
private String file_path;


@ManyToOne()
@JoinColumn(name ="course_id",nullable =false )
private Course course;

@Column
private String description;

@Column
private String status;

private LocalDateTime createdAt;
private LocalDateTime updatedAt;
 @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
