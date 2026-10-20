package com.hospital.dto.auth;

import com.hospital.enums.Role;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Long userId,
        Long profileId,
        String email,
        Role role
) {
}
