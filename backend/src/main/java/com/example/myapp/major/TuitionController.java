// package com.example.myapp.major;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.*;

// import java.math.BigDecimal;
// import java.math.RoundingMode;

// @RestController
// @RequestMapping("/api/majors")
// @RequiredArgsConstructor
// public class TuitionController {

// private final MajorRepository majorRepository;
// //
// @GetMapping("/{id}/minimum-tuition")
// public MinimumTuitionResponse getMinimumTuition(@PathVariable Long id) {
// Major major = majorRepository.findById(id)
// .orElseThrow(() -> new RuntimeException("Major not found"));
// BigDecimal totalCredits = major.getTotalCredits() != null ?
// major.getTotalCredits() : BigDecimal.ZERO;
// BigDecimal pricePerCredit = major.getPricePerCredit() != null ?
// major.getPricePerCredit() : BigDecimal.ZERO;
// BigDecimal minimumTuition = totalCredits.multiply(pricePerCredit).setScale(2,
// RoundingMode.HALF_UP);
// return new MinimumTuitionResponse(
// major.getId(),
// major.getName(),
// totalCredits,
// pricePerCredit,
// minimumTuition
// );
// }

// @Getter
// @AllArgsConstructor
// public static class MinimumTuitionResponse {
// private Long majorId;
// private String majorName;
// private BigDecimal totalCredits;
// private BigDecimal pricePerCredit;
// private BigDecimal minimumTuitionFee;
// }
// }
