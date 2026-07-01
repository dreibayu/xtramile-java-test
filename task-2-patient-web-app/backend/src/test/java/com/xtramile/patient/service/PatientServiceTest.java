package com.xtramile.patient.service;

import com.xtramile.patient.dto.PatientRequest;
import com.xtramile.patient.dto.PatientResponse;
import com.xtramile.patient.entity.Gender;
import com.xtramile.patient.entity.Patient;
import com.xtramile.patient.exception.DuplicatePidException;
import com.xtramile.patient.exception.ResourceNotFoundException;
import com.xtramile.patient.mapper.PatientMapper;
import com.xtramile.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    @Test
    void createPatientShouldSaveWhenPidIsUnique() {
        PatientRequest request = request("PID-1001");
        Patient patient = patient("PID-1001");
        PatientResponse expected = response("PID-1001");

        when(patientRepository.existsByPidIgnoreCase("PID-1001")).thenReturn(false);
        when(patientMapper.toEntity(request)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toResponse(patient)).thenReturn(expected);

        PatientResponse actual = patientService.createPatient(request);

        assertThat(actual.getPid()).isEqualTo("PID-1001");
        verify(patientRepository).save(patient);
    }

    @Test
    void createPatientShouldRejectDuplicatePid() {
        PatientRequest request = request("PID-1001");
        when(patientRepository.existsByPidIgnoreCase("PID-1001")).thenReturn(true);

        assertThatThrownBy(() -> patientService.createPatient(request))
                .isInstanceOf(DuplicatePidException.class)
                .hasMessageContaining("PID-1001");
    }

    @Test
    void getPatientShouldReturnPatientWhenFound() {
        Patient patient = patient("PID-1001");
        PatientResponse expected = response("PID-1001");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.toResponse(patient)).thenReturn(expected);

        PatientResponse actual = patientService.getPatient(1L);

        assertThat(actual.getPid()).isEqualTo("PID-1001");
    }

    @Test
    void getPatientShouldThrowWhenNotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatient(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findPatientsShouldReturnPagedResponse() {
        Patient patient = patient("PID-1001");
        PatientResponse response = response("PID-1001");
        PageRequest pageable = PageRequest.of(0, 10);

        when(patientRepository.search(eq("olivia"), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(patient), pageable, 1));
        when(patientMapper.toResponse(patient)).thenReturn(response);

        var actual = patientService.findPatients("olivia", pageable);

        assertThat(actual.getContent()).hasSize(1);
        assertThat(actual.getTotalElements()).isEqualTo(1);
    }

    @Test
    void deletePatientShouldDeleteExistingPatient() {
        Patient patient = patient("PID-1001");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(patientRepository).delete(patient);
    }

    private PatientRequest request(String pid) {
        PatientRequest request = new PatientRequest();
        request.setPid(pid);
        request.setFirstName("Olivia");
        request.setLastName("Brown");
        request.setDateOfBirth(LocalDate.of(1988, 3, 14));
        request.setGender(Gender.FEMALE);
        request.setPhoneNo("0412 345 678");
        request.setAddress("12 King Street");
        request.setSuburb("Sydney");
        request.setState("NSW");
        request.setPostcode("2000");
        return request;
    }

    private Patient patient(String pid) {
        return new Patient(pid, "Olivia", "Brown", LocalDate.of(1988, 3, 14), Gender.FEMALE,
                "0412 345 678", "12 King Street", "Sydney", "NSW", "2000");
    }

    private PatientResponse response(String pid) {
        PatientResponse response = new PatientResponse();
        response.setId(1L);
        response.setPid(pid);
        response.setFirstName("Olivia");
        response.setLastName("Brown");
        response.setFullName("Olivia Brown");
        response.setDateOfBirth(LocalDate.of(1988, 3, 14));
        response.setGender(Gender.FEMALE);
        response.setPhoneNo("0412 345 678");
        response.setAddress("12 King Street");
        response.setSuburb("Sydney");
        response.setState("NSW");
        response.setPostcode("2000");
        return response;
    }
}