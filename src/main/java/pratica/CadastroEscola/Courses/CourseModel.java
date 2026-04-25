package pratica.CadastroEscola.Courses;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import pratica.CadastroEscola.Teachers.TeacherModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_course")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseModel {

    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id")
    private UUID id;

    @Column(length = 150,
            nullable = false,
            unique = true,
            name = "name")
    private String name;

    @Column(nullable = false,
            name = "semester")
    private Integer semester;

    @Column(name = "creation_time")
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherModel teacher;
}
