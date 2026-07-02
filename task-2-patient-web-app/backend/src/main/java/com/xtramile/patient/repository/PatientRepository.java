package com.xtramile.patient.repository;

import com.xtramile.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByPidIgnoreCase(String pid);

    boolean existsByPidIgnoreCaseAndIdNot(String pid, Long id);

    @Query("""
            SELECT p FROM Patient p
            WHERE :search IS NULL OR :search = ''
               OR LOWER(p.pid) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(REPLACE(REPLACE(REPLACE(p.pid, '-', ''), ' ', ''), '_', ''))
                  LIKE LOWER(CONCAT('%', :compactSearch, '%'))
               OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(CONCAT(CONCAT(p.firstName, ' '), p.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(CONCAT(CONCAT(p.lastName, ' '), p.firstName)) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    Page<Patient> search(
            @Param("search") String search,
            @Param("compactSearch") String compactSearch,
            Pageable pageable);
}
