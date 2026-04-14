package pratica.CadastroEscola.Courses;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    @NotNull
    @Size(max = 150, message = "o nome do curso deve ter no máximo 150 caractéres.")
    private String name;

    @NotNull
    @Min(value = 1,  message = "a duração não pode ser inferior à 1 semestre")
    @Max(value = 20, message = "a duração não pode ser superior à 20 semestres")
    private Integer semester;

    private UUID teacherId;
}

