package startup.vn.patientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import startup.vn.patientservice.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
