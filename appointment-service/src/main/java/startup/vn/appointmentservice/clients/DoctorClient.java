package startup.vn.appointmentservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service", path = "/api/v1/doctors")
public interface DoctorClient {

    @GetMapping("/{id}")
    ResponseEntity<Void> getDoctorById(@PathVariable("id") Long id);
}
