package com.hospital.service.impl;

import com.hospital.entity.MedicalRecord;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.service.MedicalRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecord getById(Long medicalRecordId) {
        return medicalRecordRepository.findById(medicalRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found: " + medicalRecordId));
    }

    @Override
    public List<MedicalRecord> getForPatient(Long patientId) {
        return medicalRecordRepository.findByPatient_IdOrderByRecordedAtDesc(patientId);
    }

    @Override
    public List<MedicalRecord> getForDoctor(Long doctorId) {
        return medicalRecordRepository.findByDoctor_IdOrderByRecordedAtDesc(doctorId);
    }
}
