package com.hospital.service;

import com.hospital.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {

    List<Doctor> getAll();

    Doctor getById(Long doctorId);

    Doctor getByUserId(Long userId);

    List<Doctor> getAvailableByDepartment(Long departmentId);

    Page<Doctor> search(String keyword, Pageable pageable);
}
