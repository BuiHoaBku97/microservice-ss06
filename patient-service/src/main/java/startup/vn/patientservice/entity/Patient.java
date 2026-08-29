package startup.vn.patientservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String medicalHistory;
}
