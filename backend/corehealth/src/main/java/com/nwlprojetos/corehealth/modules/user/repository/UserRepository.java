package com.nwlprojetos.corehealth.modules.user.repository;

import com.nwlprojetos.corehealth.modules.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndTenantSlug(String email, String tenantSlug);
    boolean existsByEmailAndTenantSlug(String email, String tenantSlug);
    List<User> findAllByTenantSlug(String tenantSlug);
}
