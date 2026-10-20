package com.hospital.service;

import com.hospital.dto.doctor.CreateDoctorRequest;
import com.hospital.dto.doctor.DoctorManagementResponse;
import com.hospital.dto.doctor.UpdateDoctorRequest;

import java.util.List;

public interface AdminDoctorService {

    List<DoctorManagementResponse> getAllDoctors();

    DoctorManagementResponse getDoctorById(Long doctorId);

    DoctorManagementResponse createDoctor(CreateDoctorRequest request);

    DoctorManagementResponse updateDoctor(Long doctorId, UpdateDoctorRequest request);

    void deactivateDoctor(Long doctorId);
}
