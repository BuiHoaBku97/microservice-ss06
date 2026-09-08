package startup.vn.appointmentservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import startup.vn.appointmentservice.dto.AppointmentCreateRequest;
import startup.vn.appointmentservice.dto.AppointmentResponse;
import startup.vn.appointmentservice.entity.Appointment;
import startup.vn.appointmentservice.exception.ServiceUnavailableException;
import startup.vn.appointmentservice.repository.AppointmentRepository;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    @DisplayName("createAppointment should verify patient and doctor then persist")
    void createAppointment() {
        when(restTemplate.getForEntity(any(String.class), any(Class.class), anyMap())).thenReturn(null);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> {
            Appointment appointment = invocation.getArgument(0);
            appointment.setId(100L);
            return appointment;
        });

        AppointmentResponse response = appointmentService.createAppointment(AppointmentCreateRequest.builder()
                .patientId(1L)
                .doctorId(2L)
                .build());

        assertThat(response.getId()).isEqualTo(100L);
        assertThat(response.getPatientId()).isEqualTo(1L);
        assertThat(response.getDoctorId()).isEqualTo(2L);
        assertThat(response.getStatus()).isEqualTo("PENDING");
    }

    @Test
    @DisplayName("createAppointment should fail when patient does not exist")
    void createAppointmentPatientNotFound() {
        when(restTemplate.getForEntity(any(String.class), any(Class.class), anyMap()))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", null, null, null));

        assertThatThrownBy(() -> appointmentService.createAppointment(AppointmentCreateRequest.builder()
                .patientId(1L)
                .doctorId(2L)
                .build()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Patient not found");
    }

    @Test
    @DisplayName("createAppointment should throw ServiceUnavailableException when doctor service is unavailable")
    void createAppointmentDoctorServiceUnavailable() {
        when(restTemplate.getForEntity(any(String.class), any(Class.class), anyMap()))
                .thenReturn(null)
                .thenThrow(new org.springframework.web.client.ResourceAccessException("I/O error"));

        assertThatThrownBy(() -> appointmentService.createAppointment(AppointmentCreateRequest.builder()
                .patientId(1L)
                .doctorId(2L)
                .build()))
                .isInstanceOf(ServiceUnavailableException.class)
                .hasMessageContaining("bác sĩ");
    }

    @Test
    @DisplayName("getAppointmentById should return mapped appointment")
    void getAppointmentById() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.of(Appointment.builder()
                .id(100L)
                .patientId(1L)
                .doctorId(2L)
                .status("PENDING")
                .build()));

        var response = appointmentService.getAppointmentById(100L);

        assertThat(response.getId()).isEqualTo(100L);
        assertThat(response.getPatientId()).isEqualTo(1L);
        assertThat(response.getDoctorId()).isEqualTo(2L);
        assertThat(response.getStatus()).isEqualTo("PENDING");
    }

    @Test
    @DisplayName("getAllAppointments should map entities to response projection")
    void getAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(List.of(Appointment.builder()
                .id(100L)
                .patientId(1L)
                .doctorId(2L)
                .status("PENDING")
                .build()));

        var result = appointmentService.getAllAppointments();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(100L);
        assertThat(result.getFirst().getPatientId()).isEqualTo(1L);
        assertThat(result.getFirst().getDoctorId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("getAppointmentById should fail when appointment does not exist")
    void getAppointmentByIdNotFound() {
        when(appointmentRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.getAppointmentById(100L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Appointment not found");
    }
}
