package pratica.CadastroEscola.Students;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID> {

    Page<StudentModel> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCourseId(UUID id);
}
