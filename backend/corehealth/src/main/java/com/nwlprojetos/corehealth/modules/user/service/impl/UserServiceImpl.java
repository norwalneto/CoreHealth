package com.nwlprojetos.corehealth.modules.user.service.impl;

import com.nwlprojetos.corehealth.common.tenant.TenantGuard;
import com.nwlprojetos.corehealth.modules.auth.dto.RegisterRequest;
import com.nwlprojetos.corehealth.modules.tenant.entity.Tenant;
import com.nwlprojetos.corehealth.modules.tenant.service.TenantService;
import com.nwlprojetos.corehealth.modules.user.dto.UserResponse;
import com.nwlprojetos.corehealth.modules.user.entity.Role;
import com.nwlprojetos.corehealth.modules.user.entity.User;
import com.nwlprojetos.corehealth.modules.user.repository.UserRepository;
import com.nwlprojetos.corehealth.modules.user.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TenantService tenantService;
    private final PasswordEncoder passwordEncoder;
    private final TenantGuard tenantGuard;

    public UserServiceImpl(UserRepository repository, TenantService tenantService, PasswordEncoder passwordEncoder, TenantGuard tenantGuard) {
        this.repository = repository;
        this.tenantService = tenantService;
        this.passwordEncoder = passwordEncoder;
        this.tenantGuard = tenantGuard;
    }

    @Override
    public User create(RegisterRequest request) {
        tenantGuard.checkTenantAccess(request.tenantSlug());
        Tenant tenant = tenantService.findBySlug(request.tenantSlug());
        if (repository.existsByEmailAndTenantSlug(request.email(), request.tenantSlug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado neste tenant");
        }

        User user = new User();
        user.setTenant(tenant);
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role() == null ? Role.ROLE_USER : request.role());
        return repository.save(user);
    }

    @Override
    public List<UserResponse> listByTenant(String tenantSlug) {
        tenantGuard.checkTenantAccess(tenantSlug);
        tenantService.findBySlug(tenantSlug);
        return repository.findAllByTenantSlug(tenantSlug).stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getTenant().getId()))
                .toList();
    }
}
