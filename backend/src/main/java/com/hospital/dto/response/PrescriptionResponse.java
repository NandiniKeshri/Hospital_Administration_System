package com.hospital.dto.response;

import com.hospital.entity.Prescription;

import java.time.LocalDateTime;

public record PrescriptionResponse(
        Long id,
        Long patientId,
        Long doctorId,
        Long medicalRecordId,
        String notes,
        LocalDateTime issuedAt
) {
    public static PrescriptionResponse from(Prescription prescription) {
        Long medicalRecordId = prescription.getMedicalRecord() == null
                ? null
                : prescription.getMedicalRecord().getId();

        return new PrescriptionResponse(
                prescription.getId(),
                prescription.getPatient().getId(),
                prescription.getDoctor().getId(),
                medicalRecordId,
                prescription.getNotes(),
                prescription.getIssuedAt()
        );
    }
}
