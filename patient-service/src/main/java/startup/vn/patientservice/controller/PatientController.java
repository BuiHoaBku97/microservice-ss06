package startup.vn.patientservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import startup.vn.patientservice.dto.PatientCreateRequest;
import startup.vn.patientservice.dto.PatientResponse;
import startup.vn.patientservice.service.PatientService;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(request));
    }
}
