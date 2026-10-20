package com.hospital.repository;

import com.hospital.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUser_Id(Long userId);

    Optional<Doctor> findByUser_Email(String email);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    List<Doctor> findByAvailableForBookingTrueOrderByFullNameAsc();

    List<Doctor> findByDepartment_IdAndAvailableForBookingTrueOrderByFullNameAsc(Long departmentId);

    Page<Doctor> findByAvailableForBookingTrueAndFullNameContainingIgnoreCaseOrAvailableForBookingTrueAndSpecializationContainingIgnoreCase(
            String fullName,
            String specialization,
            Pageable pageable
    );
}
