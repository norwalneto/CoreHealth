package com.nwlprojetos.corehealth.modules.tenant.dto;

import jakarta.validation.constraints.NotBlank;

public record TenantRequest(@NotBlank String name, @NotBlank String slug) {
}
