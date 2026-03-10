package com.nwlprojetos.corehealth.modules.plan.service.impl;

import com.nwlprojetos.corehealth.modules.plan.dto.PlanRequest;
import com.nwlprojetos.corehealth.modules.plan.entity.Plan;
import com.nwlprojetos.corehealth.modules.plan.repository.PlanRepository;
import com.nwlprojetos.corehealth.modules.plan.service.PlanService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository repository;

    public PlanServiceImpl(PlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Plan create(PlanRequest request) {
        Plan plan = new Plan();
        plan.setCode(request.code());
        plan.setName(request.name());
        plan.setMonthlyPrice(request.monthlyPrice());
        plan.setMaxUsers(request.maxUsers());
        return repository.save(plan);
    }

    @Override
    public List<Plan> list() {
        return repository.findAll();
    }

    @Override
    public Plan getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado"));
    }
}
