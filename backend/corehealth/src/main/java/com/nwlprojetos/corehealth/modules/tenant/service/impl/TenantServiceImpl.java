package com.nwlprojetos.corehealth.modules.tenant.service.impl;

import com.nwlprojetos.corehealth.modules.tenant.dto.TenantRequest;
import com.nwlprojetos.corehealth.modules.tenant.entity.Tenant;
import com.nwlprojetos.corehealth.modules.tenant.repository.TenantRepository;
import com.nwlprojetos.corehealth.modules.tenant.service.TenantService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository repository;

    public TenantServiceImpl(TenantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tenant create(TenantRequest request) {
        if (repository.existsBySlug(request.slug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slug do tenant já existe");
        }

        Tenant tenant = new Tenant();
        tenant.setName(request.name());
        tenant.setSlug(request.slug());
        return repository.save(tenant);
    }

    @Override
    public List<Tenant> list() {
        return repository.findAll();
    }

    @Override
    public Tenant findBySlug(String slug) {
        return repository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant não encontrado"));
    }
}
