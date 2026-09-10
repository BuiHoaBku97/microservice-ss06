package startup.vn.appointmentservice.exception;

import java.time.LocalDateTime;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import startup.vn.appointmentservice.dto.ApiResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DOCTOR_SERVICE_UNAVAILABLE_MESSAGE =
            "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây";

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiResponseError> handleServiceUnavailable(ServiceUnavailableException ex) {
        ApiResponseError response = ApiResponseError.builder()
                .timestamp(LocalDateTime.now().withNano(0).toString())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ApiResponseError> handleOpenCircuitBreaker(CallNotPermittedException ex) {
        ApiResponseError response = ApiResponseError.builder()
                .timestamp(LocalDateTime.now().withNano(0).toString())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .message(DOCTOR_SERVICE_UNAVAILABLE_MESSAGE)
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
