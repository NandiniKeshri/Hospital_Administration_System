package com.hospital.service.impl;

import com.hospital.entity.Prescription;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.PrescriptionRepository;
import com.hospital.service.PrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Prescription getById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + prescriptionId));
    }

    @Override
    public List<Prescription> getForPatient(Long patientId) {
        return prescriptionRepository.findByPatient_IdOrderByIssuedAtDesc(patientId);
    }

    @Override
    public List<Prescription> getForDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctor_IdOrderByIssuedAtDesc(doctorId);
    }
}
