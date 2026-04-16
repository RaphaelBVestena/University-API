package pratica.CadastroEscola.Techers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("Save creates a new teacher when successful")
    public void save_PersistTeacher_WhenSuccessful(){

        TeacherModel newTeacher = createTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(newTeacher);

        Assertions.assertThat(savedTeacher).isNotNull();
        Assertions.assertThat(savedTeacher.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates the teacher when successful")
    public void save_UpdateTeacher_WhenSuccessful(){

        TeacherModel newTeacher = createTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(newTeacher);

        savedTeacher.setName("Updated Teacher test name");
        savedTeacher.setEmail("Updated Teacher test Email");
        savedTeacher.setPhone("Updated Teacher test Phone");
        savedTeacher.setGender("u");

        TeacherModel updatedTeacher = this.teacherRepository.save(savedTeacher);

        Assertions.assertThat(updatedTeacher).isNotNull();
        Assertions.assertThat(updatedTeacher.getId()).isNotNull();

        Assertions.assertThat(updatedTeacher.getId()).isEqualTo(savedTeacher.getId());
        Assertions.assertThat(updatedTeacher.getName()).isEqualTo(savedTeacher.getName());
        Assertions.assertThat(updatedTeacher.getEmail()).isEqualTo(savedTeacher.getEmail());
        Assertions.assertThat(updatedTeacher.getPhone()).isEqualTo(savedTeacher.getPhone());
        Assertions.assertThat(updatedTeacher.getGender()).isEqualTo(savedTeacher.getGender());
    }

    @Test
    @DisplayName("Delete removes teacher when successful")
    public void delete_RemovesTeacher_WhenSuccessful(){

        TeacherModel newTeacher = createTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(newTeacher);

        this.teacherRepository.deleteById(savedTeacher.getId());

        Optional<TeacherModel> teacherOptional = this.teacherRepository.findById(savedTeacher.getId());

        Assertions.assertThat(teacherOptional).isEmpty();
    }

    @Test
    @DisplayName("Exists By Email returns True when successful" )
    public void existsByEmail_ReturnsTrue_WhenSuccessful(){

        TeacherModel newTeacher = createTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(newTeacher);

        boolean existsByEmail = this.teacherRepository.existsByEmail(savedTeacher.getEmail());

        Assertions.assertThat(existsByEmail).isTrue();
        Assertions.assertThat(savedTeacher.getEmail()).isNotEmpty();
    }

    private TeacherModel createTeacher(){
        return TeacherModel.builder()
                .name("Teacher test Name")
                .email("Teacher test Email")
                .phone("teacher test Phone")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .build();
    }
}