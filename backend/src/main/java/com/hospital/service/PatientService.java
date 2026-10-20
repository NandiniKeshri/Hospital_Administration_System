
package com.hospital.service;

import com.hospital.entity.Patient;
import java.util.List;

public interface PatientService {

    Patient getById(Long patientId);

    Patient getByUserId(Long userId);

    List<Patient> getAll();
}