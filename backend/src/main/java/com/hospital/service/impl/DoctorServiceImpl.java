package com.hospital.service.impl;

import com.hospital.entity.Doctor;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DoctorRepository;
import com.hospital.service.DoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findByAvailableForBookingTrueOrderByFullNameAsc();
    }

    @Override
    public Doctor getById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + doctorId));

        if (!doctor.isAvailableForBooking()) {
            throw new ResourceNotFoundException("Doctor not found: " + doctorId);
        }
        return doctor;
    }

    @Override
    public Doctor getByUserId(Long userId) {
        return doctorRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found for user: " + userId));
    }

    @Override
    public List<Doctor> getAvailableByDepartment(Long departmentId) {
        return doctorRepository.findByDepartment_IdAndAvailableForBookingTrueOrderByFullNameAsc(departmentId);
    }

    @Override
    public Page<Doctor> search(String keyword, Pageable pageable) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        return doctorRepository.findByAvailableForBookingTrueAndFullNameContainingIgnoreCaseOrAvailableForBookingTrueAndSpecializationContainingIgnoreCase(
                normalizedKeyword,
                normalizedKeyword,
                pageable
        );
    }
}
