package com.hospital.dto.response;

import com.hospital.entity.Department;

public record DepartmentResponse(Long id, String name, String description, String location) {

    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.getLocation()
        );
    }
}
