package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
public class StaffAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    public StaffAssignment(Screening screening, Staff staff) {
        this.screening = screening;
        this.staff = staff;
    }

    public StaffAssignment() {

    }

    public Long getId() {
        return id;
    }
    public Screening getScreening() {
        return screening;
    }
    public Staff getStaff() {
        return staff;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setScreening(Screening screening) {
        this.screening = screening;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
