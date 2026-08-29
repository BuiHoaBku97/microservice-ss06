package startup.vn.doctorservice.dto;

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
public class DoctorCreateRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String specialization;

    private Integer experienceYears;

    private String email;

    private Boolean status;
}
