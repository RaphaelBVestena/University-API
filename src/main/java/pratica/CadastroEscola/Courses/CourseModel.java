package pratica.CadastroEscola.Courses;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
