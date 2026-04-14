package pratica.CadastroEscola.Students;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID> {

    boolean existsByEmail(String email);

    boolean existsByCourseId(UUID id);
}
