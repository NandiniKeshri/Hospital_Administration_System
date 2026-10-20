package com.hospital.dto.response;

import com.hospital.entity.Staff;

public record StaffResponse(Long id, String fullName, String employeeCode, String jobTitle, String phone) {

    public static StaffResponse from(Staff staff) {
        return new StaffResponse(
                staff.getId(),
                staff.getFullName(),
                staff.getEmployeeCode(),
                staff.getJobTitle(),
                staff.getPhone()
        );
    }
}
