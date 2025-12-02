package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Staff;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.model.TechnicalOperator;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class StaffService {
    private SupportStaffService supportStaffService;
    private TechnicalOperatorService technicalOperatorService;

    public StaffService(SupportStaffService supportStaffService, TechnicalOperatorService technicalOperatorService) {
        this.supportStaffService = supportStaffService;
        this.technicalOperatorService = technicalOperatorService;
    }

    public Staff resolveStaffById(Long id) {
        if (id == null) return null;

        // Logic mirroring your Converter:
        Optional<SupportStaff> support = supportStaffService.findById(id);
        if (support.isPresent()) return support.get();

        Optional<TechnicalOperator> operator = technicalOperatorService.findById(id);
        if (operator.isPresent()) return operator.get();

        return null;
    }
}
