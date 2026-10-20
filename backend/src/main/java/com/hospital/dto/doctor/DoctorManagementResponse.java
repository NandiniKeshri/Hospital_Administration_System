package com.hospital.dto.doctor;

import com.hospital.entity.Doctor;

import java.math.BigDecimal;

public record DoctorManagementResponse(
        Long id,
        Long userId,
        String email,
        Long departmentId,
        String departmentName,
        String fullName,
        String specialization,
        String licenseNumber,
        Integer yearsOfExperience,
        BigDecimal consultationFee,
        String phone,
        boolean availableForBooking,
        boolean accountActive
) {
    public static DoctorManagementResponse from(Doctor doctor) {
        return new DoctorManagementResponse(
                doctor.getId(),
                doctor.getUser().getId(),
                doctor.getUser().getEmail(),
                doctor.getDepartment().getId(),
                doctor.getDepartment().getName(),
                doctor.getFullName(),
                doctor.getSpecialization(),
                doctor.getLicenseNumber(),
                doctor.getYearsOfExperience(),
                doctor.getConsultationFee(),
                doctor.getPhone(),
                doctor.isAvailableForBooking(),
                doctor.getUser().isActive()
        );
    }
}
