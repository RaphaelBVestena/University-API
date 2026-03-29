package pratica.CadastroEscola.Students;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tb_student")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private int phone;

    @Column(name = "gender")
    private char gender;

    @CreationTimestamp
    @Column(name = "creation_time")
    private Timestamp creation_time;

    @Column(name = "birthdate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
}
