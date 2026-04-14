package pratica.CadastroEscola.Students;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class StudentDTO{
        @NotNull
        private String name;

        @NotNull
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate birthDate;

        @Email
        private String email;

        @Pattern(regexp = "^[0-9]+$")
        private String phone;

        @Pattern(regexp = "^[MFmf]$", message = "gênero deve ser M ou F")
        private String gender;

        private UUID courseId;
}
