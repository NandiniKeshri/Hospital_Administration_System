package com.hospital.controller;

import com.hospital.dto.response.StaffResponse;
import com.hospital.service.StaffService;
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
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffResponse> getStaffByUserId(@PathVariable @Positive Long userId) {
        return ResponseEntity.ok(StaffResponse.from(staffService.getByUserId(userId)));
    }

    @GetMapping("/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable @Positive Long staffId) {
        return ResponseEntity.ok(StaffResponse.from(staffService.getById(staffId)));
    }
}
