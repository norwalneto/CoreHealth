package com.nwlprojetos.corehealth.common.tenant;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TenantGuard {

    public void checkTenantAccess(String tenantSlug) {
        String tenantFromHeader = TenantContext.getTenant();
        if (tenantFromHeader != null && !tenantFromHeader.isBlank() && !tenantFromHeader.equals(tenantSlug)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tenant do header difere do tenant da operação");
        }
    }
}
