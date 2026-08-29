package startup.vn.doctorservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import startup.vn.doctorservice.dto.DoctorCreateRequest;
import startup.vn.doctorservice.dto.DoctorCreateResponse;
import startup.vn.doctorservice.dto.DoctorResponse;
import startup.vn.doctorservice.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorCreateResponse> createDoctor(@Valid @RequestBody DoctorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(request));
    }

    @GetMapping
    public List<DoctorResponse> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}
