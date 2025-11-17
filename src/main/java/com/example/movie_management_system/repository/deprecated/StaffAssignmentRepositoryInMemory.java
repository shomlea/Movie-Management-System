package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.StaffAssignment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class StaffAssignmentRepositoryInMemory extends BaseRepositoryInMemory<StaffAssignment, String> {
    public StaffAssignmentRepositoryInMemory() {
        super(StaffAssignment::getId);
    }
}
