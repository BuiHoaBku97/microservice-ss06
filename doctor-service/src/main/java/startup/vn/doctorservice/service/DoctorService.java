package startup.vn.doctorservice.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startup.vn.doctorservice.dto.DoctorCreateRequest;
import startup.vn.doctorservice.dto.DoctorCreateResponse;
import startup.vn.doctorservice.dto.DoctorResponse;
import startup.vn.doctorservice.entity.Doctor;
import startup.vn.doctorservice.repository.DoctorRepository;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional
    public DoctorCreateResponse createDoctor(DoctorCreateRequest request) {
        Doctor doctor = Doctor.builder()
                .id(request.getId())
                .name(request.getName())
                .specialization(request.getSpecialization())
                .experienceYears(request.getExperienceYears())
                .email(request.getEmail())
                .status(request.getStatus())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorCreateResponse.builder()
                .id(savedDoctor.getId())
                .name(savedDoctor.getName())
                .specialization(savedDoctor.getSpecialization())
                .experienceYears(savedDoctor.getExperienceYears())
                .email(savedDoctor.getEmail())
                .status(savedDoctor.getStatus())
                .build();
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctor -> DoctorResponse.builder()
                        .id(doctor.getId())
                        .name(doctor.getName())
                        .specialization(doctor.getSpecialization())
                        .build())
                .toList();
    }
}
