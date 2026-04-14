package pratica.CadastroEscola.Techers;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
        @NotBlank
        @Size(max = 100, message = "o nome do professor não pode conter mais de 100 caractéres.")
        private String name;

        @NotNull
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Past(message = "data de nascimento deve ser do passado")
        private LocalDate birthDate;

        @Email
        @Size(max = 100, message = "o E-mail do professor não pode conter mais de 100 caractéres.")
        private String email;

        @Pattern(regexp = "^[0-9]+$", message = "o telefone do professor só pode conter números")
        private String phone;

        @Pattern(regexp = "^[MFmf]$", message = "gênero deve ser M ou F")
        private String gender;
}
