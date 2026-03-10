package com.nwlprojetos.corehealth.modules.auth.service;

import com.nwlprojetos.corehealth.modules.auth.dto.AuthResponse;
import com.nwlprojetos.corehealth.modules.auth.dto.LoginRequest;
import com.nwlprojetos.corehealth.modules.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
