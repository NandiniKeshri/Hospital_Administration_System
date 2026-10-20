package com.hospital.dto.response;

import com.hospital.entity.Doctor;

import java.math.BigDecimal;

public record DoctorResponse(
        Long id,
        Long departmentId,
        String fullName,
        String specialization,
        Integer yearsOfExperience,
        BigDecimal consultationFee,
        boolean availableForBooking
) {
    public static DoctorResponse from(Doctor doctor) {
        Long departmentId = doctor.getDepartment() == null ? null : doctor.getDepartment().getId();
        return new DoctorResponse(
                doctor.getId(),
                departmentId,
                doctor.getFullName(),
                doctor.getSpecialization(),
                doctor.getYearsOfExperience(),
                doctor.getConsultationFee(),
                doctor.isAvailableForBooking()
        );
    }
}
