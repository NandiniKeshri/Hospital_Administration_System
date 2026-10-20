package com.hospital.service;

import com.hospital.entity.Staff;

public interface StaffService {

    Staff getById(Long staffId);

    Staff getByUserId(Long userId);
}
