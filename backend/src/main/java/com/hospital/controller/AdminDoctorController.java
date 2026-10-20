package com.hospital.controller;

import com.hospital.dto.doctor.CreateDoctorRequest;
import com.hospital.dto.doctor.DoctorManagementResponse;
import com.hospital.dto.doctor.UpdateDoctorRequest;
import com.hospital.service.AdminDoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/admin/doctors")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDoctorController {

    private final AdminDoctorService adminDoctorService;

    public AdminDoctorController(AdminDoctorService adminDoctorService) {
        this.adminDoctorService = adminDoctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorManagementResponse>> getAllDoctors() {
        return ResponseEntity.ok(adminDoctorService.getAllDoctors());
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorManagementResponse> getDoctorById(@PathVariable @Positive Long doctorId) {
        return ResponseEntity.ok(adminDoctorService.getDoctorById(doctorId));
    }

    @PostMapping
    public ResponseEntity<DoctorManagementResponse> createDoctor(@Valid @RequestBody CreateDoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDoctorService.createDoctor(request));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorManagementResponse> updateDoctor(
            @PathVariable @Positive Long doctorId,
            @Valid @RequestBody UpdateDoctorRequest request
    ) {
        return ResponseEntity.ok(adminDoctorService.updateDoctor(doctorId, request));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deactivateDoctor(@PathVariable @Positive Long doctorId) {
        adminDoctorService.deactivateDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}
