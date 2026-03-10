package com.nwlprojetos.corehealth.modules.tenant.service;

import com.nwlprojetos.corehealth.modules.tenant.dto.TenantRequest;
import com.nwlprojetos.corehealth.modules.tenant.entity.Tenant;
import java.util.List;

public interface TenantService {
    Tenant create(TenantRequest request);
    List<Tenant> list();
    Tenant findBySlug(String slug);
}
