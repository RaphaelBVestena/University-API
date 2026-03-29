package pratica.CadastroEscola.Students;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import java.sql.Timestamp;

@Entity
@Data
public class StudentModel {

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
