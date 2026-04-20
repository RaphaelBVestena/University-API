package pratica.CadastroEscola.Teachers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "name",
        "birthDate",
        "email",
        "phone",
        "gender",
        "creationTime",
        "updateTime"
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO {

        private UUID id;

        private String name;

        private LocalDate birthDate;

        private String email;

        private String phone;

        private String gender;

        private LocalDateTime creationTime;

        private LocalDateTime updateTime;
}
