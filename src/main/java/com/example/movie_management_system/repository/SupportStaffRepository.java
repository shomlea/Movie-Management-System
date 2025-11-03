package com.example.movie_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportStaffRepository extends JpaRepository<StaffRepository, String> {

}
