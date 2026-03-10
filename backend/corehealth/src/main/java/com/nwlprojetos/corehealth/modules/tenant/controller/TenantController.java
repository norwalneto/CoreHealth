package com.nwlprojetos.corehealth.modules.tenant.controller;

import com.nwlprojetos.corehealth.modules.tenant.dto.TenantRequest;
import com.nwlprojetos.corehealth.modules.tenant.entity.Tenant;
import com.nwlprojetos.corehealth.modules.tenant.service.TenantService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public Tenant create(@RequestBody @Valid TenantRequest request) {
        return tenantService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public List<Tenant> list() {
        return tenantService.list();
    }
}
