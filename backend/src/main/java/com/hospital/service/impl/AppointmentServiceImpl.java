package com.hospital.service.impl;

import com.hospital.entity.Appointment;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment getById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + appointmentId));
    }

    @Override
    public List<Appointment> getForPatient(Long patientId) {
        return appointmentRepository.findByPatient_IdOrderByAppointmentDateDescAppointmentTimeDesc(patientId);
    }

    @Override
    public List<Appointment> getForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor_IdOrderByAppointmentDateAscAppointmentTimeAsc(doctorId);
    }

    @Override
    public List<Appointment> getForDoctorOnDate(Long doctorId, LocalDate appointmentDate) {
        return appointmentRepository.findByDoctor_IdAndAppointmentDateOrderByAppointmentTimeAsc(doctorId, appointmentDate);
    }

    @Override
    public boolean isDoctorSlotBooked(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime) {
        return appointmentRepository.existsByDoctor_IdAndAppointmentDateAndAppointmentTime(
                doctorId,
                appointmentDate,
                appointmentTime
        );
    }
}
