package com.hospital.service;

import com.hospital.entity.User;

public interface UserService {

    User getById(Long userId);

    User getByEmail(String email);

    boolean emailExists(String email);
}
