package pratica.CadastroEscola.Techers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    @DisplayName("Exists By Email returns False when successful" )
    public void existsByEmail_ReturnsFalse_WhenSuccessful(){

        TeacherModel newTeacher = createTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(newTeacher);

        boolean existsByEmail = this.teacherRepository.existsByEmail("non-existent email");

        Assertions.assertThat(existsByEmail).isFalse();
        Assertions.assertThat(savedTeacher.getEmail()).isNotEmpty();
    }

    @Test
    @DisplayName("FindAll returns 2 pages with content when successful" )
    public void findAll_returns2PagesWithContent_WhenSuccessful(){
        int amount = 10;

        this.teacherRepository.saveAll(createTeachersList(amount));

        Page<TeacherModel> teachersPage1 = this.teacherRepository.findAll(PageRequest.of(0, amount/2));
        Page<TeacherModel> teachersPage2 = this.teacherRepository.findAll(PageRequest.of(1, amount/2));

        Assertions.assertThat(teachersPage1).isNotEmpty();
        Assertions.assertThat(teachersPage2).isNotEmpty();

        Assertions.assertThat(teachersPage1.getNumberOfElements()).isEqualTo(amount/2);
        Assertions.assertThat(teachersPage2.getNumberOfElements()).isEqualTo(amount/2);

        Assertions.assertThat(teachersPage1.getNumber()).isEqualTo(0);
        Assertions.assertThat(teachersPage2.getNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("FindAll returns a empty page when successful" )
    public void findAll_ReturnsEmptyPage_WhenSuccessful(){

        Page<TeacherModel> teachersPage = this.teacherRepository.findAll(PageRequest.of(0, 20));

        Assertions.assertThat(teachersPage).isNotNull();
        Assertions.assertThat(teachersPage.getContent()).isEmpty();
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

    private List<TeacherModel> createTeachersList(Integer amount){
        List<TeacherModel> teacherModels = new ArrayList<>();

        for (int i = 0; i <= amount; i++){
            teacherModels.add(TeacherModel.builder()
                    .id(null)
                    .name(String.valueOf(i))
                    .email(null)
                    .phone(String.valueOf(i))
                    .gender("T")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .creationTime(null)
                    .updateTime(null)
                    .build());
        }

        return teacherModels;
    }
}