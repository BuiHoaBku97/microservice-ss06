package startup.vn.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import startup.vn.appointmentservice.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
