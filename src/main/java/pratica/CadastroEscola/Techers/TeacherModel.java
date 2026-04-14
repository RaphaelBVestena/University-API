package pratica.CadastroEscola.Techers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_teacher")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherModel {

    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name",
            nullable = false,
            length = 100)
    private String name;

    @Column(name = "email",
            length = 100,
            unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender",
            length = 1)
    private String gender;

    @Column(name = "birth_date",
            nullable = false)
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
