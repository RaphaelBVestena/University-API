package pratica.CadastroEscola.Courses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TeacherSummaryDTO {
    private UUID id;
    private String name;
}
