package com.hospital.service.impl;

import com.hospital.dto.doctor.CreateDoctorRequest;
import com.hospital.dto.doctor.DoctorManagementResponse;
import com.hospital.dto.doctor.UpdateDoctorRequest;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.enums.Role;
import com.hospital.exception.DuplicateResourceException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;
import com.hospital.service.AdminDoctorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class AdminDoctorServiceImpl implements AdminDoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDoctorServiceImpl(
            DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorManagementResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(DoctorManagementResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorManagementResponse getDoctorById(Long doctorId) {
        return DoctorManagementResponse.from(findDoctor(doctorId));
    }

    @Override
    @Transactional
    public DoctorManagementResponse createDoctor(CreateDoctorRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
        String normalizedLicenseNumber = request.licenseNumber().trim();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateResourceException("An account already exists for this email address");
        }
        if (doctorRepository.findByLicenseNumber(normalizedLicenseNumber).isPresent()) {
            throw new DuplicateResourceException("A doctor already exists with this license number");
        }

        Department department = findDepartment(request.departmentId());
        User user = new User();
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(request.temporaryPassword()));
        user.setRole(Role.DOCTOR);
        user = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        applyDoctorDetails(
                doctor,
                request.fullName(),
                department,
                request.specialization(),
                normalizedLicenseNumber,
                request.yearsOfExperience(),
                request.consultationFee(),
                request.phone(),
                true
        );

        return DoctorManagementResponse.from(doctorRepository.save(doctor));
    }

    @Override
    @Transactional
    public DoctorManagementResponse updateDoctor(Long doctorId, UpdateDoctorRequest request) {
        Doctor doctor = findDoctor(doctorId);
        String normalizedLicenseNumber = request.licenseNumber().trim();

        doctorRepository.findByLicenseNumber(normalizedLicenseNumber)
                .filter(existingDoctor -> !existingDoctor.getId().equals(doctorId))
                .ifPresent(existingDoctor -> {
                    throw new DuplicateResourceException("A doctor already exists with this license number");
                });

        Department department = findDepartment(request.departmentId());
        applyDoctorDetails(
                doctor,
                request.fullName(),
                department,
                request.specialization(),
                normalizedLicenseNumber,
                request.yearsOfExperience(),
                request.consultationFee(),
                request.phone(),
                request.availableForBooking()
        );

        return DoctorManagementResponse.from(doctorRepository.save(doctor));
    }

    @Override
    @Transactional
    public void deactivateDoctor(Long doctorId) {
        Doctor doctor = findDoctor(doctorId);
        doctor.setAvailableForBooking(false);
        doctor.getUser().setActive(false);
    }

    private Doctor findDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + doctorId));
    }

    private Department findDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + departmentId));
    }

    private void applyDoctorDetails(
            Doctor doctor,
            String fullName,
            Department department,
            String specialization,
            String licenseNumber,
            Integer yearsOfExperience,
            BigDecimal consultationFee,
            String phone,
            boolean availableForBooking
    ) {
        doctor.setFullName(fullName.trim());
        doctor.setDepartment(department);
        doctor.setSpecialization(specialization.trim());
        doctor.setLicenseNumber(licenseNumber);
        doctor.setYearsOfExperience(yearsOfExperience);
        doctor.setConsultationFee(consultationFee);
        doctor.setPhone(phone.trim());
        doctor.setAvailableForBooking(availableForBooking);
    }
}
