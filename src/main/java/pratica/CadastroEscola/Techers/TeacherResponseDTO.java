package pratica.CadastroEscola.Techers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
