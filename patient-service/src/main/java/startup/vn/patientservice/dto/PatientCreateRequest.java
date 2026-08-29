package startup.vn.patientservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String address;

    @NotBlank
    private String medicalHistory;
}
