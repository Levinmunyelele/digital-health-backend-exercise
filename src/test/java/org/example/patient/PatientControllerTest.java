package org.example.patient;

import org.example.patient.controller.PatientController;
import org.example.patient.model.Patient;
import org.example.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PatientService patientService;

        @Test
        void shouldCreatePatientSuccessfully() throws Exception {
                // Arrange: Mock service response
                Patient mockPatient = new Patient();
                mockPatient.setId(1L);
                mockPatient.setIdentifier("TEST123");
                mockPatient.setGivenName("John");
                mockPatient.setFamilyName("Doe");
                mockPatient.setBirthDate(LocalDate.parse("1995-05-10"));
                mockPatient.setGender("M");

                when(patientService.create(any(Patient.class))).thenReturn(mockPatient);

                String json = """
                                {
                                  "identifier": "TEST123",
                                  "givenName": "John",
                                  "familyName": "Doe",
                                  "birthDate": "1995-05-10",
                                  "gender": "M"
                                }
                                """;

                // Act & Assert
                mockMvc.perform(post("/api/patients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.identifier").value("TEST123"))
                                .andExpect(jsonPath("$.givenName").value("John"));
        }

        @Test
        void shouldRejectInvalidPatient() throws Exception {
                // Invalid input JSON
                String invalidJson = """
                                {
                                  "identifier": "",
                                  "givenName": "",
                                  "familyName": "",
                                  "birthDate": "2030-01-01",
                                  "gender": ""
                                }
                                """;

                // Act & Assert
                mockMvc.perform(post("/api/patients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson))
                                .andExpect(status().isBadRequest());
        }
}
