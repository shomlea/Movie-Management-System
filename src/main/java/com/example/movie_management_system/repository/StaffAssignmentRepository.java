package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.StaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment,Long> {
    Optional<StaffAssignment> findByScreeningIdAndStaffId(Long screeningId, Long staffId);
}
