package pratica.CadastroEscola.Techers;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
public class TeacherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private int phone;
    private char gender;
    private Timestamp creation_time;
    private Date birthDate;
}
