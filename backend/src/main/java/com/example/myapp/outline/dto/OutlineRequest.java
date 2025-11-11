package com.example.myapp.outline.dto;
import org.springframework.web.multipart.MultipartFile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OutlineRequest{
    private MultipartFile filePath;
    private Long course_id;
    private String description;
    private String status;
}