package startup.vn.appointmentservice.service;

import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import startup.vn.appointmentservice.clients.DoctorClient;
import startup.vn.appointmentservice.clients.PatientClient;
import startup.vn.appointmentservice.exception.ServiceUnavailableException;

@Service
@RequiredArgsConstructor
public class PatientAvailabilityService {
    private final PatientClient patientClient;

    @Retry(name = "patientRetry", fallbackMethod = "patientServiceUnavailable")
    public void validatePatientExists(Long patientId) {
        patientClient.getPatientById(patientId);
    }

    private void patientServiceUnavailable(Long patientId, Throwable throwable) {
        if (throwable instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "không tìm thấy bệnh nhân");
        }

        throw new ServiceUnavailableException(
                "Hiện tại không thể kiểm tra thông tin bệnh nhân, vui lòng thử lại sau vài giây");
    }
}
