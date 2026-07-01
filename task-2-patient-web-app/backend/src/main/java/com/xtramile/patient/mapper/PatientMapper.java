package com.xtramile.patient.mapper;

import com.xtramile.patient.dto.PatientRequest;
import com.xtramile.patient.dto.PatientResponse;
import com.xtramile.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest request) {
        return new Patient(
                request.getPid(),
                request.getFirstName(),
                request.getLastName(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getPhoneNo(),
                request.getAddress(),
                request.getSuburb(),
                request.getState(),
                request.getPostcode()
        );
    }

    public void updateEntity(Patient patient, PatientRequest request) {
        patient.setPid(request.getPid());
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setPhoneNo(request.getPhoneNo());
        patient.setAddress(request.getAddress());
        patient.setSuburb(request.getSuburb());
        patient.setState(request.getState());
        patient.setPostcode(request.getPostcode());
    }

    public PatientResponse toResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setPid(patient.getPid());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setFullName(patient.getFirstName() + " " + patient.getLastName());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setPhoneNo(patient.getPhoneNo());
        response.setAddress(patient.getAddress());
        response.setSuburb(patient.getSuburb());
        response.setState(patient.getState());
        response.setPostcode(patient.getPostcode());
        return response;
    }
}