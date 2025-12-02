package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.StaffAssignment;
import com.example.movie_management_system.service.StaffAssignmentService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class StaffAssignmentConverter implements Converter<Long, StaffAssignment> {

    private final StaffAssignmentService staffAssignmentService;

    public StaffAssignmentConverter(StaffAssignmentService staffAssignmentService) {
        this.staffAssignmentService = staffAssignmentService;
    }

    @Override
    public StaffAssignment convert(Long id) {
        if (id == null) return null;

        return staffAssignmentService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("StaffAssignment with ID " + id + " not found."));
    }
}