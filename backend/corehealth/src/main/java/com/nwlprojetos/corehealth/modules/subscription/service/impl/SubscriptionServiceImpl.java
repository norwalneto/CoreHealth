package com.nwlprojetos.corehealth.modules.subscription.service.impl;

import com.nwlprojetos.corehealth.common.tenant.TenantGuard;
import com.nwlprojetos.corehealth.modules.plan.entity.Plan;
import com.nwlprojetos.corehealth.modules.plan.service.PlanService;
import com.nwlprojetos.corehealth.modules.subscription.dto.SubscriptionRequest;
import com.nwlprojetos.corehealth.modules.subscription.entity.Subscription;
import com.nwlprojetos.corehealth.modules.subscription.entity.SubscriptionStatus;
import com.nwlprojetos.corehealth.modules.subscription.repository.SubscriptionRepository;
import com.nwlprojetos.corehealth.modules.subscription.service.SubscriptionService;
import com.nwlprojetos.corehealth.modules.tenant.entity.Tenant;
import com.nwlprojetos.corehealth.modules.tenant.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository repository;
    private final TenantService tenantService;
    private final PlanService planService;
    private final TenantGuard tenantGuard;

    public SubscriptionServiceImpl(SubscriptionRepository repository, TenantService tenantService, PlanService planService, TenantGuard tenantGuard) {
        this.repository = repository;
        this.tenantService = tenantService;
        this.planService = planService;
        this.tenantGuard = tenantGuard;
    }

    @Override
    public Subscription createForTenant(String tenantSlug, SubscriptionRequest request) {
        tenantGuard.checkTenantAccess(tenantSlug);
        Tenant tenant = tenantService.findBySlug(tenantSlug);
        if (repository.findByTenantSlug(tenantSlug).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tenant já possui assinatura ativa/cadastrada");
        }

        Plan plan = planService.getById(request.planId());
        Subscription subscription = new Subscription();
        subscription.setTenant(tenant);
        subscription.setPlan(plan);
        subscription.setStartsAt(request.startsAt());
        subscription.setEndsAt(request.endsAt());
        subscription.setStatus(request.status() == null ? SubscriptionStatus.ACTIVE : request.status());
        return repository.save(subscription);
    }

    @Override
    public Subscription getByTenant(String tenantSlug) {
        tenantGuard.checkTenantAccess(tenantSlug);
        tenantService.findBySlug(tenantSlug);
        return repository.findByTenantSlug(tenantSlug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assinatura não encontrada"));
    }
}
