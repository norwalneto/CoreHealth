package com.nwlprojetos.corehealth.modules.subscription.service;

import com.nwlprojetos.corehealth.modules.subscription.dto.SubscriptionRequest;
import com.nwlprojetos.corehealth.modules.subscription.entity.Subscription;

public interface SubscriptionService {
    Subscription createForTenant(String tenantSlug, SubscriptionRequest request);
    Subscription getByTenant(String tenantSlug);
}
