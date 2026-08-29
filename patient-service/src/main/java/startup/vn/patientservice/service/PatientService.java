package startup.vn.patientservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import startup.vn.patientservice.dto.PatientCreateRequest;
import startup.vn.patientservice.dto.PatientResponse;
import startup.vn.patientservice.entity.Patient;
import startup.vn.patientservice.repository.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public PatientResponse createPatient(PatientCreateRequest request) {
        Patient patient = Patient.builder()
                .id(request.getId())
                .fullName(request.getFullName())
                .address(request.getAddress())
                .medicalHistory(request.getMedicalHistory())
                .build();

        Patient savedPatient = patientRepository.save(patient);
        return toResponse(savedPatient);
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        return toResponse(patient);
    }

    private PatientResponse toResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
}
