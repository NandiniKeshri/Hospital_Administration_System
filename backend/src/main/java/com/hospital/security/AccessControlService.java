package com.hospital.security;

import com.hospital.entity.Appointment;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Prescription;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.PrescriptionRepository;
import com.hospital.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("accessControl")
@Transactional(readOnly = true)
public class AccessControlService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;

    public AccessControlService(
            UserRepository userRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            MedicalRecordRepository medicalRecordRepository,
            PrescriptionRepository prescriptionRepository
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public boolean isCurrentUser(Long userId, String email) {
        return userRepository.findById(userId)
                .map(user -> user.getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }

    public boolean isPatientOwner(Long patientId, String email) {
        return patientRepository.findById(patientId)
                .map(patient -> patient.getUser().getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }

    public boolean isDoctorOwner(Long doctorId, String email) {
        return doctorRepository.findById(doctorId)
                .map(doctor -> doctor.getUser().getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }

    public boolean canCurrentDoctorAccessPatient(Long patientId, String email) {
        return doctorRepository.findByUser_Email(email)
                .map(doctor -> appointmentRepository.existsByDoctor_User_EmailAndPatient_Id(email, patientId))
                .orElse(false);
    }

    public boolean canReadAppointment(Long appointmentId, String email) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> isAppointmentParticipant(appointment, email))
                .orElse(false);
    }

    public boolean canReadMedicalRecord(Long medicalRecordId, String email) {
        return medicalRecordRepository.findById(medicalRecordId)
                .map(record -> isMedicalRecordParticipant(record, email))
                .orElse(false);
    }

    public boolean canReadPrescription(Long prescriptionId, String email) {
        return prescriptionRepository.findById(prescriptionId)
                .map(prescription -> isPrescriptionParticipant(prescription, email))
                .orElse(false);
    }

    private boolean isAppointmentParticipant(Appointment appointment, String email) {
        return appointment.getPatient().getUser().getEmail().equalsIgnoreCase(email)
                || appointment.getDoctor().getUser().getEmail().equalsIgnoreCase(email);
    }

    private boolean isMedicalRecordParticipant(MedicalRecord record, String email) {
        return isPatientOwner(record.getPatient().getId(), email)
                || canCurrentDoctorAccessPatient(record.getPatient().getId(), email);
    }

    private boolean isPrescriptionParticipant(Prescription prescription, String email) {
        return isPatientOwner(prescription.getPatient().getId(), email)
                || canCurrentDoctorAccessPatient(prescription.getPatient().getId(), email);
    }
}
