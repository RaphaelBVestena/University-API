package pratica.CadastroEscola.Techers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherModel, Long> {
}
