package pratica.CadastroEscola.Courses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID> {

    Page<CourseModel> findAll(Pageable pageable);

    boolean existsByName(String name);
}
