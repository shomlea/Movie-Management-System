package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.SupportStaff;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class SupportStaffRepositoryInFile extends BaseRepositoryInFile<SupportStaff, String>{
    public SupportStaffRepositoryInFile() {
        super(SupportStaff::getId, "data/support_staff.json", SupportStaff.class);
    }
}
