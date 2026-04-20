package pratica.CadastroEscola.Teachers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherModel, UUID> {

    Page<TeacherModel> findAll(Pageable pageable);

    Boolean existsByEmail(String email);
}
