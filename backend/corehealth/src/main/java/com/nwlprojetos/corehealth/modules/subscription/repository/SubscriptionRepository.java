package com.nwlprojetos.corehealth.modules.subscription.repository;

import com.nwlprojetos.corehealth.modules.subscription.entity.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByTenantSlug(String tenantSlug);
}
