package com.hospital.service.impl;

import com.hospital.entity.Staff;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.StaffRepository;
import com.hospital.service.StaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff getById(Long staffId) {
        return staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff member not found: " + staffId));
    }

    @Override
    public Staff getByUserId(Long userId) {
        return staffRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found for user: " + userId));
    }
}
