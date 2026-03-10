package com.nwlprojetos.corehealth.modules.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PlanRequest(
        @NotBlank String code,
        @NotBlank String name,
        @NotNull BigDecimal monthlyPrice,
        @NotNull Integer maxUsers
) {
}
