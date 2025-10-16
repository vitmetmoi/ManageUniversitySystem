package com.example.myapp.major.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long facultyId;
    private String facultyName;
    private java.math.BigDecimal pricePerCredit;
}


