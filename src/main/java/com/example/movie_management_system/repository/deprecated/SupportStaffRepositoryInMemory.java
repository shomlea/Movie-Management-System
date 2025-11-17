package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.SupportStaff;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class SupportStaffRepositoryInMemory extends BaseRepositoryInMemory<SupportStaff, String> {
    public SupportStaffRepositoryInMemory() {
        super(SupportStaff::getId);
    }
}
