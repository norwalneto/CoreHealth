package com.nwlprojetos.corehealth.modules.plan.controller;

import com.nwlprojetos.corehealth.modules.plan.dto.PlanRequest;
import com.nwlprojetos.corehealth.modules.plan.entity.Plan;
import com.nwlprojetos.corehealth.modules.plan.service.PlanService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('OWNER')")
    public Plan create(@RequestBody @Valid PlanRequest request) {
        return planService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public List<Plan> list() {
        return planService.list();
    }
}
