package pratica.CadastroEscola.Students;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CourseSummaryDTO {

    private UUID id;

    private String name;
}
