package com.nwlprojetos.corehealth.modules.subscription.dto;

import com.nwlprojetos.corehealth.modules.subscription.entity.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SubscriptionRequest(
        @NotNull Long planId,
        @NotNull LocalDate startsAt,
        @NotNull LocalDate endsAt,
        SubscriptionStatus status
) {
}
