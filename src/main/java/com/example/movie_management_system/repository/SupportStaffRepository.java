package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.SupportStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupportStaffRepository extends JpaRepository<SupportStaff,Long>, JpaSpecificationExecutor<SupportStaff> {
    Optional<SupportStaff> findByName(String name);
}
