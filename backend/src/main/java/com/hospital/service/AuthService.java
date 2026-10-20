package com.hospital.service;

import com.hospital.dto.auth.AuthResponse;
import com.hospital.dto.auth.LoginRequest;
import com.hospital.dto.auth.RegisterPatientRequest;

public interface AuthService {

    AuthResponse registerPatient(RegisterPatientRequest request);

    AuthResponse login(LoginRequest request);
}
