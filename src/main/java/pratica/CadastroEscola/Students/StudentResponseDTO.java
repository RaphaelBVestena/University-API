package pratica.CadastroEscola.Students;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import pratica.CadastroEscola.Courses.CourseModel;

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
        "course",
        "creationTime",
        "updateTime"
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {

        private UUID id;

        private String name;
        private LocalDate birthDate;
        private String email;
        private String phone;
        private String gender;

        private CourseSummaryDTO course;

        private LocalDateTime creationTime;
        private LocalDateTime updateTime;

}
