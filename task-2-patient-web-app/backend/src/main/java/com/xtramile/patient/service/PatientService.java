package com.xtramile.patient.service;

import com.xtramile.patient.dto.PagedResponse;
import com.xtramile.patient.dto.PatientRequest;
import com.xtramile.patient.dto.PatientResponse;
import com.xtramile.patient.entity.Patient;
import com.xtramile.patient.exception.DuplicatePidException;
import com.xtramile.patient.exception.ResourceNotFoundException;
import com.xtramile.patient.mapper.PatientMapper;
import com.xtramile.patient.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Transactional(readOnly = true)
    public PagedResponse<PatientResponse> findPatients(String search, Pageable pageable) {
        String keyword = search == null ? "" : search.trim();
        Page<PatientResponse> page = patientRepository.search(keyword, pageable)
                .map(patientMapper::toResponse);
        return PagedResponse.from(page);
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatient(Long id) {
        Patient patient = findPatientById(id);
        return patientMapper.toResponse(patient);
    }

    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByPidIgnoreCase(request.getPid())) {
            throw new DuplicatePidException(request.getPid());
        }
        Patient patient = patientMapper.toEntity(request);
        return patientMapper.toResponse(patientRepository.save(patient));
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = findPatientById(id);
        if (patientRepository.existsByPidIgnoreCaseAndIdNot(request.getPid(), id)) {
            throw new DuplicatePidException(request.getPid());
        }
        patientMapper.updateEntity(patient, request);
        return patientMapper.toResponse(patientRepository.save(patient));
    }

    public void deletePatient(Long id) {
        Patient patient = findPatientById(id);
        patientRepository.delete(patient);
    }

    private Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }
}