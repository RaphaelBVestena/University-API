package pratica.CadastroEscola.Techers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherModel, UUID> {

    Boolean existsByEmail(String email);
}
