package com.nwlprojetos.corehealth.modules.plan.service;

import com.nwlprojetos.corehealth.modules.plan.dto.PlanRequest;
import com.nwlprojetos.corehealth.modules.plan.entity.Plan;
import java.util.List;

public interface PlanService {
    Plan create(PlanRequest request);
    List<Plan> list();
    Plan getById(Long id);
}
