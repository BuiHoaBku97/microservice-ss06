package startup.vn.doctorservice.dto;

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
public class DoctorCreateResponse {

    private Long id;
    private String name;
    private String specialization;
    private Integer experienceYears;
    private String email;
    private Boolean status;
}
