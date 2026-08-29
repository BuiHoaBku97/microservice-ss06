package startup.vn.doctorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import startup.vn.doctorservice.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
