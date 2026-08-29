package startup.vn.appointmentservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import startup.vn.appointmentservice.dto.AppointmentResponse;
import startup.vn.appointmentservice.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @Test
    @DisplayName("POST /api/v1/appointments should create appointment")
    void createAppointment() throws Exception {
        when(appointmentService.createAppointment(any())).thenReturn(AppointmentResponse.builder()
                .id(100L)
                .patientId(1L)
                .doctorId(2L)
                .status("PENDING")
                .build());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new AppointmentController(appointmentService)).build();

        mockMvc.perform(post("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "patientId": 1,
                                  "doctorId": 2
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.patientId").value(1L))
                .andExpect(jsonPath("$.doctorId").value(2L))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("GET /api/v1/appointments/{id} should return appointment")
    void getAppointmentById() throws Exception {
        when(appointmentService.getAppointmentById(100L)).thenReturn(AppointmentResponse.builder()
                .id(100L)
                .patientId(1L)
                .doctorId(2L)
                .status("PENDING")
                .build());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new AppointmentController(appointmentService)).build();

        mockMvc.perform(get("/api/v1/appointments/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.patientId").value(1L))
                .andExpect(jsonPath("$.doctorId").value(2L))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("GET /api/v1/appointments should return all appointments")
    void getAllAppointments() throws Exception {
        when(appointmentService.getAllAppointments()).thenReturn(List.of(AppointmentResponse.builder()
                .id(100L)
                .patientId(1L)
                .doctorId(2L)
                .status("PENDING")
                .build()));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new AppointmentController(appointmentService)).build();

        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[0].patientId").value(1L))
                .andExpect(jsonPath("$[0].doctorId").value(2L))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
}
