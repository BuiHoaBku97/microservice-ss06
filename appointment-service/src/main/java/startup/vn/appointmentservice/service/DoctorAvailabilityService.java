package startup.vn.appointmentservice.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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

    // Annotation order: RateLimiter -> CircuitBreaker -> Retry -> fallbackMethod
    @RateLimiter(name = "doctorServiceRateLimiter")
    @CircuitBreaker(name = "doctorServiceCB")
    @Retry(name = "doctorServiceRetry", fallbackMethod = "getDoctorFallback")
    public void validateDoctorExists(Long doctorId) {
        doctorClient.getDoctorById(doctorId);
    }

    // fallback invoked after retries are exhausted
    public void getDoctorFallback(Long doctorId, Throwable throwable) {
        if (throwable instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }

        throw new ServiceUnavailableException(
                "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây");
    }
}

