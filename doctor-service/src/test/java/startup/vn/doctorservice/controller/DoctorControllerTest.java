package startup.vn.doctorservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import startup.vn.doctorservice.dto.DoctorCreateResponse;
import startup.vn.doctorservice.dto.DoctorResponse;
import startup.vn.doctorservice.service.DoctorService;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @Test
    @DisplayName("POST /api/v1/doctors should create a doctor")
    void createDoctor() throws Exception {
        when(doctorService.createDoctor(org.mockito.ArgumentMatchers.any())).thenReturn(
                DoctorCreateResponse.builder()
                        .id(10L)
                        .name("Dr. C")
                        .specialization("Nhi khoa")
                        .experienceYears(8)
                        .email("dr.c@example.com")
                        .status(true)
                        .build()
        );

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new DoctorController(doctorService)).build();

        mockMvc.perform(post("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 10,
                                  "name": "Dr. C",
                                  "specialization": "Nhi khoa",
                                  "experienceYears": 8,
                                  "email": "dr.c@example.com",
                                  "status": true
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("Dr. C"))
                .andExpect(jsonPath("$.specialization").value("Nhi khoa"))
                .andExpect(jsonPath("$.experienceYears").value(8))
                .andExpect(jsonPath("$.email").value("dr.c@example.com"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    @DisplayName("GET /api/v1/doctors should return doctor list")
    void getAllDoctors() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(List.of(
                DoctorResponse.builder().id(1L).name("Dr. A").specialization("Noi khoa").build(),
                DoctorResponse.builder().id(2L).name("Dr. B").specialization("Ngoai khoa").build()
        ));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new DoctorController(doctorService)).build();

        mockMvc.perform(get("/api/v1/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Dr. A"))
                .andExpect(jsonPath("$[0].specialization").value("Noi khoa"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Dr. B"))
                .andExpect(jsonPath("$[1].specialization").value("Ngoai khoa"));
    }
}
