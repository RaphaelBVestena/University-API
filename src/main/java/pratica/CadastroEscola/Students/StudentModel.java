package pratica.CadastroEscola.Students;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import pratica.CadastroEscola.Courses.CourseModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentModel {

    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name",
            length = 100,
            nullable = false)
    private String name;

    @Column(name = "email",
            unique = true,
            length = 100)
    private String email;

    @Column(name = "phone",
            length = 20)
    private String phone;

    @Column(name = "gender")
    private String gender;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "birth_date",
            nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseModel course;
}
