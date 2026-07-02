package com.xtramile.patient.repository;

import com.xtramile.patient.entity.Gender;
import com.xtramile.patient.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository.saveAll(List.of(
                new Patient(
                        "PID-1001",
                        "Olivia",
                        "Brown",
                        LocalDate.of(1988, 3, 14),
                        Gender.FEMALE,
                        "0412 345 678",
                        "12 King Street",
                        "Sydney",
                        "NSW",
                        "2000"),
                new Patient(
                        "PID-1004",
                        "James",
                        "Lee",
                        LocalDate.of(1968, 1, 9),
                        Gender.MALE,
                        "08 8123 4567",
                        "9 North Terrace",
                        "Adelaide",
                        "SA",
                        "5000")));
    }

    @Test
    void searchShouldMatchPartialPid() {
        var page = patientRepository.search("1001", "1001", PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Patient::getPid)
                .containsExactly("PID-1001");
    }

    @Test
    void searchShouldMatchPartialFirstName() {
        var page = patientRepository.search("jame", "jame", PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Patient::getFirstName)
                .containsExactly("James");
    }

    @Test
    void searchShouldMatchPidWithoutSeparators() {
        var page = patientRepository.search("pid1001", "pid1001", PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Patient::getPid)
                .containsExactly("PID-1001");
    }

    @Test
    void searchShouldMatchPidWithDifferentSeparators() {
        var page = patientRepository.search("PID 1001", "PID1001", PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Patient::getPid)
                .containsExactly("PID-1001");
    }

    @Test
    void searchShouldMatchFullNameInEitherOrder() {
        var page = patientRepository.search("Brown Olivia", "BrownOlivia", PageRequest.of(0, 10));

        assertThat(page.getContent())
                .extracting(Patient::getPid)
                .containsExactly("PID-1001");
    }
}
