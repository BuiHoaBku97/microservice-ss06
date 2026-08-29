package startup.vn.doctorservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import startup.vn.doctorservice.dto.DoctorCreateRequest;
import startup.vn.doctorservice.entity.Doctor;
import startup.vn.doctorservice.repository.DoctorRepository;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    @DisplayName("createDoctor should persist and map request data")
    void createDoctor() {
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = doctorService.createDoctor(DoctorCreateRequest.builder()
                .id(10L)
                .name("Dr. C")
                .specialization("Nhi khoa")
                .experienceYears(8)
                .email("dr.c@example.com")
                .status(true)
                .build());

        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getName()).isEqualTo("Dr. C");
        assertThat(response.getSpecialization()).isEqualTo("Nhi khoa");
        assertThat(response.getExperienceYears()).isEqualTo(8);
        assertThat(response.getEmail()).isEqualTo("dr.c@example.com");
        assertThat(response.getStatus()).isTrue();
    }

    @Test
    @DisplayName("getDoctorById should return mapped doctor")
    void getDoctorById() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(Doctor.builder()
                .id(1L)
                .name("Dr. A")
                .specialization("Noi khoa")
                .build()));

        var response = doctorService.getDoctorById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Dr. A");
        assertThat(response.getSpecialization()).isEqualTo("Noi khoa");
    }

    @Test
    @DisplayName("getAllDoctors should map entities to response projection")
    void getAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(
                Doctor.builder().id(1L).name("Dr. A").specialization("Noi khoa").experienceYears(10).build()
        ));

        var result = doctorService.getAllDoctors();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(1L);
        assertThat(result.getFirst().getName()).isEqualTo("Dr. A");
        assertThat(result.getFirst().getSpecialization()).isEqualTo("Noi khoa");
    }
}
