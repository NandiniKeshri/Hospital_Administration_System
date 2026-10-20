package com.hospital.dto.doctor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateDoctorRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 120, message = "Full name must not exceed 120 characters")
        String fullName,

        @NotNull(message = "Department is required")
        @Positive(message = "Department ID must be positive")
        Long departmentId,

        @NotBlank(message = "Specialization is required")
        @Size(max = 120, message = "Specialization must not exceed 120 characters")
        String specialization,

        @NotBlank(message = "License number is required")
        @Size(max = 60, message = "License number must not exceed 60 characters")
        String licenseNumber,

        @NotNull(message = "Years of experience is required")
        @Min(value = 0, message = "Years of experience cannot be negative")
        @Max(value = 70, message = "Years of experience is unrealistic")
        Integer yearsOfExperience,

        @NotNull(message = "Consultation fee is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Consultation fee must be greater than zero")
        BigDecimal consultationFee,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[0-9+() -]{7,20}$", message = "Phone number must be valid")
        String phone,

        boolean availableForBooking
) {
}
