package com.nwlprojetos.corehealth.modules.subscription.controller;

import com.nwlprojetos.corehealth.modules.subscription.dto.SubscriptionRequest;
import com.nwlprojetos.corehealth.modules.subscription.entity.Subscription;
import com.nwlprojetos.corehealth.modules.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{tenantSlug}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public Subscription create(@PathVariable String tenantSlug, @RequestBody @Valid SubscriptionRequest request) {
        return subscriptionService.createForTenant(tenantSlug, request);
    }

    @GetMapping("/{tenantSlug}")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public Subscription getByTenant(@PathVariable String tenantSlug) {
        return subscriptionService.getByTenant(tenantSlug);
    }
}
