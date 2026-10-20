package com.hospital.controller;

import com.hospital.dto.response.PatientResponse;
import com.hospital.service.PatientService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@Validated
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isCurrentUser(#userId, authentication.name)")
    public ResponseEntity<PatientResponse> getPatientByUserId(@PathVariable @Positive Long userId) {
        return ResponseEntity.ok(PatientResponse.from(patientService.getByUserId(userId)));
    }

    @GetMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isPatientOwner(#patientId, authentication.name) "
            + "or @accessControl.canCurrentDoctorAccessPatient(#patientId, authentication.name)")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable @Positive Long patientId) {
        return ResponseEntity.ok(PatientResponse.from(patientService.getById(patientId)));
    }
}
