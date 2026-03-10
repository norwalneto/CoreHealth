package com.nwlprojetos.corehealth.security;

import com.nwlprojetos.corehealth.modules.user.entity.User;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails {

    private final User user;

    public AppUserDetails(User user) {
        this.user = user;
    }

    public Long getTenantId() {
        return user.getTenant().getId();
    }

    public String getTenantSlug() {
        return user.getTenant().getSlug();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail() + "|" + user.getTenant().getSlug();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
