package com.nwlprojetos.corehealth.modules.user.dto;

import com.nwlprojetos.corehealth.modules.user.entity.Role;

public record UserResponse(Long id, String name, String email, Role role, Long tenantId) {
}
