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
                            "08 8123 4567", "9 North Terrace", "Adelaide", "SA", "5000"),
                    new Patient("PID-1005", "Mia", "Martin", LocalDate.of(1992, 5, 18), Gender.FEMALE,
                            "02 9012 4567", "4 George Street", "Parramatta", "NSW", "2150"),
                    new Patient("PID-1006", "Liam", "Anderson", LocalDate.of(1981, 12, 6), Gender.MALE,
                            "03 8345 1290", "18 Swanston Street", "Carlton", "VIC", "3053"),
                    new Patient("PID-1007", "Sophia", "Thomas", LocalDate.of(1974, 9, 30), Gender.FEMALE,
                            "07 3555 7812", "44 Adelaide Street", "Fortitude Valley", "QLD", "4006"),
                    new Patient("PID-1008", "Ethan", "Walker", LocalDate.of(2000, 2, 11), Gender.MALE,
                            "08 9222 3344", "31 Hay Street", "Perth", "WA", "6000"),
                    new Patient("PID-1009", "Isabella", "Hall", LocalDate.of(1986, 8, 27), Gender.FEMALE,
                            "08 8463 2211", "7 Rundle Mall", "Norwood", "SA", "5067"),
                    new Patient("PID-1010", "Lucas", "Allen", LocalDate.of(1990, 4, 3), Gender.MALE,
                            "02 6234 7788", "22 London Circuit", "Canberra", "ACT", "2601"),
                    new Patient("PID-1011", "Charlotte", "Young", LocalDate.of(1977, 10, 15), Gender.FEMALE,
                            "03 6231 9900", "16 Elizabeth Street", "Hobart", "TAS", "7000"),
                    new Patient("PID-1012", "Mason", "King", LocalDate.of(1965, 6, 21), Gender.MALE,
                            "08 8941 3345", "55 Smith Street", "Darwin", "NT", "0800"),
                    new Patient("PID-1013", "Amelia", "Wright", LocalDate.of(1998, 1, 4), Gender.FEMALE,
                            "02 9876 5544", "10 Church Street", "Newcastle", "NSW", "2300"),
                    new Patient("PID-1014", "Henry", "Scott", LocalDate.of(1983, 3, 29), Gender.MALE,
                            "03 5222 7788", "2 Moorabool Street", "Geelong", "VIC", "3220"),
                    new Patient("PID-1015", "Harper", "Green", LocalDate.of(1971, 11, 12), Gender.FEMALE,
                            "07 4771 6633", "90 Flinders Street", "Townsville", "QLD", "4810"),
                    new Patient("PID-1016", "Benjamin", "Baker", LocalDate.of(1994, 7, 8), Gender.MALE,
                            "08 9721 1188", "13 Victoria Street", "Bunbury", "WA", "6230"),
                    new Patient("PID-1017", "Evelyn", "Adams", LocalDate.of(1989, 9, 19), Gender.FEMALE,
                            "08 8723 4455", "6 Commercial Street", "Mount Gambier", "SA", "5290"),
                    new Patient("PID-1018", "William", "Nelson", LocalDate.of(1976, 2, 25), Gender.MALE,
                            "02 6285 1133", "27 Anketell Street", "Tuggeranong", "ACT", "2900"),
                    new Patient("PID-1019", "Grace", "Carter", LocalDate.of(2001, 5, 7), Gender.FEMALE,
                            "03 6424 5522", "39 Best Street", "Devonport", "TAS", "7310"),
                    new Patient("PID-1020", "Jack", "Mitchell", LocalDate.of(1969, 12, 17), Gender.MALE,
                            "08 8952 7788", "8 Todd Mall", "Alice Springs", "NT", "0870"),
                    new Patient("PID-1021", "Chloe", "Perez", LocalDate.of(1996, 4, 22), Gender.FEMALE,
                            "02 4228 9911", "50 Crown Street", "Wollongong", "NSW", "2500"),
                    new Patient("PID-1022", "Samuel", "Roberts", LocalDate.of(1980, 8, 2), Gender.MALE,
                            "03 5441 2200", "3 Pall Mall", "Bendigo", "VIC", "3550"),
                    new Patient("PID-1023", "Zoe", "Turner", LocalDate.of(1993, 6, 13), Gender.OTHER,
                            "07 4031 8899", "71 Sheridan Street", "Cairns", "QLD", "4870"),
                    new Patient("PID-1024", "Daniel", "Phillips", LocalDate.of(1985, 10, 31), Gender.MALE,
                            "08 9842 1001", "11 York Street", "Albany", "WA", "6330")
            ));
        };
    }
}
