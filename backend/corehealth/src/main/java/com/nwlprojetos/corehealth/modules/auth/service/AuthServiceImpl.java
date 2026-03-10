package com.nwlprojetos.corehealth.modules.auth.service;

import com.nwlprojetos.corehealth.common.tenant.TenantGuard;
import com.nwlprojetos.corehealth.modules.auth.dto.AuthResponse;
import com.nwlprojetos.corehealth.modules.auth.dto.LoginRequest;
import com.nwlprojetos.corehealth.modules.auth.dto.RegisterRequest;
import com.nwlprojetos.corehealth.modules.user.entity.User;
import com.nwlprojetos.corehealth.modules.user.repository.UserRepository;
import com.nwlprojetos.corehealth.modules.user.service.UserService;
import com.nwlprojetos.corehealth.security.AppUserDetails;
import com.nwlprojetos.corehealth.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TenantGuard tenantGuard;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, JwtService jwtService, TenantGuard tenantGuard) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tenantGuard = tenantGuard;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        tenantGuard.checkTenantAccess(request.tenantSlug());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email() + "|" + request.tenantSlug(), request.password())
        );
        User user = userRepository.findByEmailAndTenantSlug(request.email(), request.tenantSlug())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));
        return new AuthResponse(jwtService.generateToken(new AppUserDetails(user), request.tenantSlug()));
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = userService.create(request);
        return new AuthResponse(jwtService.generateToken(new AppUserDetails(user), request.tenantSlug()));
    }
}
