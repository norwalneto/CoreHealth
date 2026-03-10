package com.nwlprojetos.corehealth.modules.user.service;

import com.nwlprojetos.corehealth.modules.auth.dto.RegisterRequest;
import com.nwlprojetos.corehealth.modules.user.dto.UserResponse;
import com.nwlprojetos.corehealth.modules.user.entity.User;
import java.util.List;

public interface UserService {
    User create(RegisterRequest request);
    List<UserResponse> listByTenant(String tenantSlug);
}
