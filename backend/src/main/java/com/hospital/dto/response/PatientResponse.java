package com.hospital.dto.response;

import com.hospital.entity.Patient;
import com.hospital.enums.Gender;

import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String fullName,
        String phone,
        LocalDate dateOfBirth,
        Gender gender,
        String bloodGroup,
        String address
) {
    public static PatientResponse from(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFullName(),
                patient.getPhone(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getBloodGroup(),
                patient.getAddress()
        );
    }
}
