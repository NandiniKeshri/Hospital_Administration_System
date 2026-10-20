package com.hospital.controller;

import com.hospital.dto.response.DoctorResponse;
import com.hospital.service.DoctorService;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAll().stream()
                .map(DoctorResponse::from)
                .toList();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DoctorResponse>> searchDoctors(
            @RequestParam(defaultValue = "") String q,
            Pageable pageable
    ) {
        return ResponseEntity.ok(doctorService.search(q, pageable).map(DoctorResponse::from));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorResponse>> getAvailableDoctorsByDepartment(@PathVariable @Positive Long departmentId) {
        List<DoctorResponse> doctors = doctorService.getAvailableByDepartment(departmentId).stream()
                .map(DoctorResponse::from)
                .toList();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable @Positive Long doctorId) {
        return ResponseEntity.ok(DoctorResponse.from(doctorService.getById(doctorId)));
    }
}
