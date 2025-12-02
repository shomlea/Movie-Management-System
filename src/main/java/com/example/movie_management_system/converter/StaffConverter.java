package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Staff;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.service.SupportStaffService;
import com.example.movie_management_system.service.TechnicalOperatorService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class StaffConverter implements Converter<Long, Staff> {

    private final SupportStaffService supportStaffService;
    private final TechnicalOperatorService technicalOperatorService;

    public StaffConverter(SupportStaffService supportStaffService, TechnicalOperatorService technicalOperatorService) {
        this.supportStaffService = supportStaffService;
        this.technicalOperatorService = technicalOperatorService;
    }

    @Override
    public Staff convert(Long id) {
        if (id == null) return null;

        // Logic mirrors the findStaffById helper method
        Optional<SupportStaff> foundSupportStaff = supportStaffService.findById(id);
        if (foundSupportStaff.isPresent()) {
            return foundSupportStaff.get();
        }

        Optional<TechnicalOperator> foundTechnicalOperator = technicalOperatorService.findById(id);
        if (foundTechnicalOperator.isPresent()) {
            return foundTechnicalOperator.get();
        }

        throw new NoSuchElementException("Staff with ID " + id + " not found.");
    }
}