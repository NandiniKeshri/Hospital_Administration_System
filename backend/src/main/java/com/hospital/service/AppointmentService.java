package com.hospital.service;

import com.hospital.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    Appointment getById(Long appointmentId);

    List<Appointment> getForPatient(Long patientId);

    List<Appointment> getForDoctor(Long doctorId);

    List<Appointment> getForDoctorOnDate(Long doctorId, LocalDate appointmentDate);

    boolean isDoctorSlotBooked(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime);
}
