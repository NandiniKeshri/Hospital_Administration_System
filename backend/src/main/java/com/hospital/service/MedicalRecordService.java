package com.hospital.service;

import com.hospital.entity.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecord getById(Long medicalRecordId);

    List<MedicalRecord> getForPatient(Long patientId);

    List<MedicalRecord> getForDoctor(Long doctorId);
}
