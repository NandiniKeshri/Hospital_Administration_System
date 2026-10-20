package com.hospital.repository;

import com.hospital.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient_IdOrderByRecordedAtDesc(Long patientId);

    List<MedicalRecord> findByDoctor_IdOrderByRecordedAtDesc(Long doctorId);

    Optional<MedicalRecord> findByAppointment_Id(Long appointmentId);
}
