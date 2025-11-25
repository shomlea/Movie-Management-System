package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.StaffAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class StaffAssignmentRepositoryInFile extends BaseRepositoryInFile<StaffAssignment, String>{
    public StaffAssignmentRepositoryInFile() {
        super(StaffAssignment::getId, "data/staff_assignments.json", StaffAssignment.class);
    }
}
