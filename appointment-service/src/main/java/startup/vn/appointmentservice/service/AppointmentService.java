package startup.vn.appointmentservice.service;

import java.util.Map;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import startup.vn.appointmentservice.dto.AppointmentCreateRequest;
import startup.vn.appointmentservice.dto.AppointmentResponse;
import startup.vn.appointmentservice.entity.Appointment;
import startup.vn.appointmentservice.repository.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private static final String PATIENT_SERVICE_URL = "http://patient-service/api/v1/patients/{id}";
    private static final String DOCTOR_SERVICE_URL = "http://doctor-service/api/v1/doctors/{id}";

    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public AppointmentResponse createAppointment(AppointmentCreateRequest request) {
        validatePatientExists(request.getPatientId());
        validateDoctorExists(request.getDoctorId());

        Appointment appointment = Appointment.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .status("PENDING")
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return toResponse(savedAppointment);
    }

    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        return toResponse(appointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private void validatePatientExists(Long patientId) {
        try {
            restTemplate.getForEntity(PATIENT_SERVICE_URL, String.class, Map.of("id", patientId));
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
    }

    private void validateDoctorExists(Long doctorId) {
        try {
            restTemplate.getForEntity(DOCTOR_SERVICE_URL, String.class, Map.of("id", doctorId));
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .build();
    }
}
