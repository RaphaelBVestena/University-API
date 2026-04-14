package pratica.CadastroEscola.Courses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@JsonPropertyOrder({
        "id",
        "name",
        "semester",
        "teacher",
        "creationTime",
        "updateTime"
})
@Data
@Builder
public class CourseResponseDTO {

    private UUID id;

    private String name;
    private Integer semester;

    private TeacherSummaryDTO teacher;

    private LocalDateTime creationTime;
    private LocalDateTime updateTime;

}

