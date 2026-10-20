package com.hospital.dto.response;

import com.hospital.entity.MedicalRecord;

import java.time.LocalDateTime;

public record MedicalRecordResponse(
        Long id,
        Long patientId,
        Long doctorId,
        Long appointmentId,
        String diagnosis,
        String symptoms,
        String notes,
        String vitals,
        LocalDateTime recordedAt
) {
    public static MedicalRecordResponse from(MedicalRecord medicalRecord) {
        Long appointmentId = medicalRecord.getAppointment() == null ? null : medicalRecord.getAppointment().getId();
        return new MedicalRecordResponse(
                medicalRecord.getId(),
                medicalRecord.getPatient().getId(),
                medicalRecord.getDoctor().getId(),
                appointmentId,
                medicalRecord.getDiagnosis(),
                medicalRecord.getSymptoms(),
                medicalRecord.getNotes(),
                medicalRecord.getVitals(),
                medicalRecord.getRecordedAt()
        );
    }
}
