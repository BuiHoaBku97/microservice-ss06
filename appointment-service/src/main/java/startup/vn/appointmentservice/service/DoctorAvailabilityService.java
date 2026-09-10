package startup.vn.appointmentservice.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import startup.vn.appointmentservice.clients.DoctorClient;
import startup.vn.appointmentservice.exception.ServiceUnavailableException;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorClient doctorClient;

    @CircuitBreaker(name = "doctorServiceCB", fallbackMethod = "doctorServiceUnavailable")
    public void validateDoctorExists(Long doctorId) {
        doctorClient.getDoctorById(doctorId);
    }

    private void doctorServiceUnavailable(Long doctorId, Throwable throwable) {
        if (throwable instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        throw new ServiceUnavailableException(
                "Doctor service is currently unavailable. Please try scheduling again later.");
    }
}
