package com.example.myapp.major.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorRequest {
    private String code;
    private String name;
    private String description;
    private Long facultyId; // specify parent faculty when creating/updating
    private java.math.BigDecimal pricePerCredit;
}


