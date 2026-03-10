package com.nwlprojetos.corehealth.modules.auth.dto;

import com.nwlprojetos.corehealth.modules.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String tenantSlug,
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        Role role
) {
}
