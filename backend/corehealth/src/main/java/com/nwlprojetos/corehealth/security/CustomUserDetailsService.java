package com.nwlprojetos.corehealth.security;

import com.nwlprojetos.corehealth.modules.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        String[] principalParts = username.split("\\|", 2);
        if (principalParts.length != 2) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Principal inválido");
        }

        String email = principalParts[0];
        String tenantSlug = principalParts[1];

        return userRepository.findByEmailAndTenantSlug(email, tenantSlug)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
}
