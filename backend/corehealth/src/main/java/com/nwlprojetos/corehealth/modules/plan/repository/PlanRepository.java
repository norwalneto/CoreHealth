package com.nwlprojetos.corehealth.modules.plan.repository;

import com.nwlprojetos.corehealth.modules.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
