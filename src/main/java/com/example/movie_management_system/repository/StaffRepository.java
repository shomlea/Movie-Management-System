package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffRepository {
    private List<Staff> staff;

    public StaffRepository() {
        this.staff = new ArrayList<>();
    }

    public List<Staff> getAllStaff() {
        return staff;
    }

    public Staff getStaffById(String id){
        for (Staff staff : staff) {
            if (staff.getId().equals(id)) {
                return staff;
            }
        }
        return null;
    }

    public void addStaff(Staff staff){
        this.staff.add(staff);
    }

}
