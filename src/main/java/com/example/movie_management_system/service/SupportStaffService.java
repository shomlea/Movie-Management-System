package com.example.movie_management_system.service;

import com.example.movie_management_system.dto.SupportStaffFilterDto;
import com.example.movie_management_system.model.Role;
import com.example.movie_management_system.model.SupportStaff;
import com.example.movie_management_system.model.TechnicalOperator;
import com.example.movie_management_system.repository.SupportStaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportStaffService {
    private SupportStaffRepository supportStaffRepository;

    public SupportStaffService(SupportStaffRepository supportStaffRepository) {
        this.supportStaffRepository = supportStaffRepository;
    }
    @Transactional
    public SupportStaff save(SupportStaff supportStaff) {
        Optional<SupportStaff> foundSupportStaff = supportStaffRepository.findByName(supportStaff.getName());
        if(foundSupportStaff.isPresent())
            throw new DataIntegrityViolationException("There is already a Theatre with that name");
        return supportStaffRepository.save(supportStaff);
    }
    @Transactional
    public SupportStaff update(Long id, SupportStaff updatedStaff) {
        SupportStaff existingStaff = supportStaffRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("SupportStaff with ID " + id + " not found."));

        Optional<SupportStaff> foundSupportStaff = supportStaffRepository.findByName(existingStaff.getName());
        if(foundSupportStaff.isPresent() && !foundSupportStaff.get().getId().equals(existingStaff.getId()))
            throw new DataIntegrityViolationException("There is already a Theatre with that name");

        existingStaff.setName(updatedStaff.getName());
        existingStaff.setSalary(updatedStaff.getSalary());
        existingStaff.setRole(updatedStaff.getRole());

        return supportStaffRepository.save(existingStaff);
    }
    @Transactional
    public void delete(Long id) {
        if (!supportStaffRepository.existsById(id)) {
            throw new NoSuchElementException("SupportStaff with ID " + id + " not found.");
        }
        supportStaffRepository.deleteById(id);
    }
    public List<SupportStaff> findAll() {
        return supportStaffRepository.findAll();
    }

    public Page<SupportStaff> findAll(SupportStaffFilterDto filter, Pageable pageable) {

        if (filter.isEmpty()) {
            return supportStaffRepository.findAll(pageable);
        }

        Specification<SupportStaff> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getNameQuery() != null && !filter.getNameQuery().trim().isEmpty()) {
            final String namePattern = "%" + filter.getNameQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), namePattern)
            );
        }

        if (filter.getRole() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("role"), filter.getRole())
            );
        }

        if (filter.getMinSalary() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("salary"), filter.getMinSalary())
            );
        }

        if (filter.getMaxSalary() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("salary"), filter.getMaxSalary())
            );
        }

        return supportStaffRepository.findAll(spec, pageable);
    }

    public Optional<SupportStaff> findById(Long id) {
        return supportStaffRepository.findById(id);
    }

}
