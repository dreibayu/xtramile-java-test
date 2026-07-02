package com.xtramile.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtramile.patient.dto.PagedResponse;
import com.xtramile.patient.dto.PatientRequest;
import com.xtramile.patient.dto.PatientResponse;
import com.xtramile.patient.entity.Gender;
import com.xtramile.patient.exception.GlobalExceptionHandler;
import com.xtramile.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@Import(GlobalExceptionHandler.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Test
    void findPatientsShouldReturnPage() throws Exception {
        PagedResponse<PatientResponse> page = new PagedResponse<>(List.of(response()), 0, 10, 1, 1, true);
        when(patientService.findPatients(eq("olivia"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/patients")
                        .param("page", "0")
                        .param("size", "10")
                        .param("search", "olivia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].pid").value("PID-1001"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getPatientShouldReturnPatient() throws Exception {
        when(patientService.getPatient(1L)).thenReturn(response());

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value("PID-1001"));
    }

    @Test
    void createPatientShouldReturnCreated() throws Exception {
        when(patientService.createPatient(any(PatientRequest.class))).thenReturn(response());

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.pid").value("PID-1001"));
    }

    @Test
    void updatePatientShouldReturnUpdatedPatient() throws Exception {
        when(patientService.updatePatient(eq(1L), any(PatientRequest.class))).thenReturn(response());

        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Olivia Brown"));
    }

    @Test
    void deletePatientShouldReturnNoContent() throws Exception {
        doNothing().when(patientService).deletePatient(1L);

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createPatientShouldReturnBadRequestForInvalidPostcode() throws Exception {
        PatientRequest request = request();
        request.setPostcode("20A0");

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.postcode").exists());
    }

    private PatientRequest request() {
        PatientRequest request = new PatientRequest();
        request.setPid("PID-1001");
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

    private PatientResponse response() {
        PatientResponse response = new PatientResponse();
        response.setId(1L);
        response.setPid("PID-1001");
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