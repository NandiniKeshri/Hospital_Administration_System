package com.hospital.controller;

import com.hospital.dto.response.PrescriptionResponse;
import com.hospital.service.PrescriptionService;
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
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isPatientOwner(#patientId, authentication.name) "
            + "or @accessControl.canCurrentDoctorAccessPatient(#patientId, authentication.name)")
    public ResponseEntity<List<PrescriptionResponse>> getPatientPrescriptions(@PathVariable @Positive Long patientId) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getForPatient(patientId).stream()
                .map(PrescriptionResponse::from)
                .toList();
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isDoctorOwner(#doctorId, authentication.name)")
    public ResponseEntity<List<PrescriptionResponse>> getDoctorPrescriptions(@PathVariable @Positive Long doctorId) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getForDoctor(doctorId).stream()
                .map(PrescriptionResponse::from)
                .toList();
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/{prescriptionId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.canReadPrescription(#prescriptionId, authentication.name)")
    public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable @Positive Long prescriptionId) {
        return ResponseEntity.ok(PrescriptionResponse.from(prescriptionService.getById(prescriptionId)));
    }
}
