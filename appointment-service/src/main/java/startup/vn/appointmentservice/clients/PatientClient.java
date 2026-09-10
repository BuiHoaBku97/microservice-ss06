package startup.vn.appointmentservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="patient-service", path = "/api/v1/patients")
public interface PatientClient {
    @GetMapping("/{id}")
    public ResponseEntity<Void> getPatientById(@PathVariable Long id);
}
