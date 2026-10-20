package com.hospital.controller;

import com.hospital.dto.response.AppointmentResponse;
import com.hospital.service.AppointmentService;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isPatientOwner(#patientId, authentication.name) "
            + "or @accessControl.canCurrentDoctorAccessPatient(#patientId, authentication.name)")
    public ResponseEntity<List<AppointmentResponse>> getPatientAppointments(@PathVariable @Positive Long patientId) {
        List<AppointmentResponse> appointments = appointmentService.getForPatient(patientId).stream()
                .map(AppointmentResponse::from)
                .toList();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isDoctorOwner(#doctorId, authentication.name)")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments(@PathVariable @Positive Long doctorId) {
        List<AppointmentResponse> appointments = appointmentService.getForDoctor(doctorId).stream()
                .map(AppointmentResponse::from)
                .toList();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}/date")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.isDoctorOwner(#doctorId, authentication.name)")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointmentsOnDate(
            @PathVariable @Positive Long doctorId,
            @RequestParam @FutureOrPresent LocalDate date
    ) {
        List<AppointmentResponse> appointments = appointmentService.getForDoctorOnDate(doctorId, date).stream()
                .map(AppointmentResponse::from)
                .toList();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @RequestParam @Positive Long doctorId,
            @RequestParam @FutureOrPresent LocalDate date,
            @RequestParam LocalTime time
    ) {
        boolean available = !appointmentService.isDoctorSlotBooked(doctorId, date, time);
        return ResponseEntity.ok(Map.of("available", available));
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN') or @accessControl.canReadAppointment(#appointmentId, authentication.name)")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable @Positive Long appointmentId) {
        return ResponseEntity.ok(AppointmentResponse.from(appointmentService.getById(appointmentId)));
    }
}
