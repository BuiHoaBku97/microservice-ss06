package startup.vn.patientservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import startup.vn.patientservice.dto.PatientCreateRequest;
import startup.vn.patientservice.dto.PatientResponse;
import startup.vn.patientservice.entity.Patient;
import startup.vn.patientservice.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    @DisplayName("createPatient should persist and map request data")
    void createPatient() {
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PatientResponse response = patientService.createPatient(PatientCreateRequest.builder()
                .id(1L)
                .fullName("Nguyen Van A")
                .address("Ha Noi")
                .medicalHistory("Di ung thuoc")
                .build());

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFullName()).isEqualTo("Nguyen Van A");
        assertThat(response.getAddress()).isEqualTo("Ha Noi");
        assertThat(response.getMedicalHistory()).isEqualTo("Di ung thuoc");
    }

    @Test
    @DisplayName("getPatientById should return mapped patient")
    void getPatientById() {
        when(patientRepository.findById(1L)).thenReturn(java.util.Optional.of(Patient.builder()
                .id(1L)
                .fullName("Nguyen Van A")
                .address("Ha Noi")
                .medicalHistory("Di ung thuoc")
                .build()));

        PatientResponse response = patientService.getPatientById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFullName()).isEqualTo("Nguyen Van A");
        assertThat(response.getAddress()).isEqualTo("Ha Noi");
        assertThat(response.getMedicalHistory()).isEqualTo("Di ung thuoc");
    }
}
