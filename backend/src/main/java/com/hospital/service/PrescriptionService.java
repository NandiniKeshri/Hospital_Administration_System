package com.hospital.service;

import com.hospital.entity.Prescription;

import java.util.List;

public interface PrescriptionService {

    Prescription getById(Long prescriptionId);

    List<Prescription> getForPatient(Long patientId);

    List<Prescription> getForDoctor(Long doctorId);
}
