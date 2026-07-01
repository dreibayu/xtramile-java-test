package com.xtramile.patient.config;

import com.xtramile.patient.entity.Gender;
import com.xtramile.patient.entity.Patient;
import com.xtramile.patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedPatients(PatientRepository patientRepository) {
        return args -> {
            if (patientRepository.count() > 0) {
                return;
            }

            patientRepository.saveAll(List.of(
                    new Patient("PID-1001", "Olivia", "Brown", LocalDate.of(1988, 3, 14), Gender.FEMALE,
                            "0412 345 678", "12 King Street", "Sydney", "NSW", "2000"),
                    new Patient("PID-1002", "Noah", "Wilson", LocalDate.of(1979, 7, 2), Gender.MALE,
                            "03 9123 4567", "80 Collins Street", "Melbourne", "VIC", "3000"),
                    new Patient("PID-1003", "Ava", "Taylor", LocalDate.of(1995, 11, 23), Gender.FEMALE,
                            "07 3123 9876", "25 Queen Street", "Brisbane", "QLD", "4000"),
                    new Patient("PID-1004", "James", "Lee", LocalDate.of(1968, 1, 9), Gender.MALE,
                            "08 8123 4567", "9 North Terrace", "Adelaide", "SA", "5000")
            ));
        };
    }
}