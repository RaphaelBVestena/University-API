package pratica.CadastroEscola.Students;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, UUID> {

    Page<StudentModel> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCourseId(UUID id);
}
