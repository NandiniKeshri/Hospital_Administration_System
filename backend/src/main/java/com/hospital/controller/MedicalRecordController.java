package com.hospital.controller;

import com.hospital.dto.response.MedicalRecordResponse;
import com.hospital.service.MedicalRecordService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isPatientOwner(#patientId, authentication.name) "
            + "or @accessControl.canCurrentDoctorAccessPatient(#patientId, authentication.name)")
    public ResponseEntity<List<MedicalRecordResponse>> getPatientRecords(@PathVariable @Positive Long patientId) {
        List<MedicalRecordResponse> records = medicalRecordService.getForPatient(patientId).stream()
                .map(MedicalRecordResponse::from)
                .toList();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isDoctorOwner(#doctorId, authentication.name)")
    public ResponseEntity<List<MedicalRecordResponse>> getDoctorRecords(@PathVariable @Positive Long doctorId) {
        List<MedicalRecordResponse> records = medicalRecordService.getForDoctor(doctorId).stream()
                .map(MedicalRecordResponse::from)
                .toList();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{medicalRecordId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.canReadMedicalRecord(#medicalRecordId, authentication.name)")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecordById(@PathVariable @Positive Long medicalRecordId) {
        return ResponseEntity.ok(MedicalRecordResponse.from(medicalRecordService.getById(medicalRecordId)));
    }
}
