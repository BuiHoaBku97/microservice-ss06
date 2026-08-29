package startup.vn.patientservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import startup.vn.patientservice.dto.PatientResponse;
import startup.vn.patientservice.service.PatientService;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Test
    @DisplayName("POST /api/v1/patients should create a patient")
    void createPatient() throws Exception {
        when(patientService.createPatient(any())).thenReturn(PatientResponse.builder()
                .id(1L)
                .fullName("Nguyen Van A")
                .address("Ha Noi")
                .medicalHistory("Di ung thuoc")
                .build());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new PatientController(patientService)).build();

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "fullName": "Nguyen Van A",
                                  "address": "Ha Noi",
                                  "medicalHistory": "Di ung thuoc"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.address").value("Ha Noi"))
                .andExpect(jsonPath("$.medicalHistory").value("Di ung thuoc"));
    }
}
