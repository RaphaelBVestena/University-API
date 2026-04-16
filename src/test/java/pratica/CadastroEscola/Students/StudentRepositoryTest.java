package pratica.CadastroEscola.Students;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pratica.CadastroEscola.Students.StudentModel;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Save creates a new Student when successful")
    public void save_PersistStudent_WhenSuccessful(){

        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates the Student when successful")
    public void save_UpdateStudent_WhenSuccessful(){

        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        savedStudent.setName("Updated Student test name");
        savedStudent.setEmail("Updated Student test Email");
        savedStudent.setPhone("Updated Student test Phone");
        savedStudent.setGender("u");

        StudentModel updatedStudent = this.studentRepository.save(savedStudent);

        Assertions.assertThat(updatedStudent).isNotNull();
        Assertions.assertThat(updatedStudent.getId()).isNotNull();

        Assertions.assertThat(updatedStudent.getId()).isEqualTo(savedStudent.getId());
        Assertions.assertThat(updatedStudent.getName()).isEqualTo(savedStudent.getName());
        Assertions.assertThat(updatedStudent.getEmail()).isEqualTo(savedStudent.getEmail());
        Assertions.assertThat(updatedStudent.getPhone()).isEqualTo(savedStudent.getPhone());
        Assertions.assertThat(updatedStudent.getGender()).isEqualTo(savedStudent.getGender());
    }

    @Test
    @DisplayName("Delete removes student when successful")
    public void delete_RemovesStudent_WhenSuccessful(){

        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        this.studentRepository.deleteById(savedStudent.getId());

        Optional<StudentModel> studentOptional = this.studentRepository.findById(savedStudent.getId());

        Assertions.assertThat(studentOptional).isEmpty();
    }

    @Test
    @DisplayName("Exists By Email returns True when successful" )
    public void existsByEmail_ReturnsTrue_WhenSuccessful(){

        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        boolean existsByEmail = this.studentRepository.existsByEmail(savedStudent.getEmail());

        Assertions.assertThat(existsByEmail).isTrue();
        Assertions.assertThat(savedStudent.getEmail()).isNotEmpty();
    }

    @Test
    @DisplayName("Exists By Email returns False when successful" )
    public void existsByEmail_ReturnsFalse_WhenSuccessful(){

        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        boolean existsByEmail = this.studentRepository.existsByEmail("non-existent email");

        Assertions.assertThat(existsByEmail).isFalse();
        Assertions.assertThat(savedStudent.getEmail()).isNotEmpty();
    }

    @Test
    @DisplayName("FindAll returns 2 pages with content when successful" )
    public void findAll_returns2PagesWithContent_WhenSuccessful(){
        int amount = 10;

        this.studentRepository.saveAll(createStudentsList(amount));

        Page<StudentModel> studentsPage1 = this.studentRepository.findAll(PageRequest.of(0, amount/2));
        Page<StudentModel> studentsPage2 = this.studentRepository.findAll(PageRequest.of(1, amount/2));

        Assertions.assertThat(studentsPage1).isNotEmpty();
        Assertions.assertThat(studentsPage2).isNotEmpty();

        Assertions.assertThat(studentsPage1.getNumberOfElements()).isEqualTo(amount/2);
        Assertions.assertThat(studentsPage2.getNumberOfElements()).isEqualTo(amount/2);

        Assertions.assertThat(studentsPage1.getNumber()).isEqualTo(0);
        Assertions.assertThat(studentsPage2.getNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("FindAll returns a empty page when successful" )
    public void findAll_ReturnsEmptyPage_WhenSuccessful(){

        Page<StudentModel> studentsPage = this.studentRepository.findAll(PageRequest.of(0, 20));
        
        Assertions.assertThat(studentsPage).isNotNull();
        Assertions.assertThat(studentsPage.getContent()).isEmpty();
    }

    @Test
    @DisplayName("exists By Course Id returns true when successful")
    void existsByCourseId_ReturnsTrue_WhenSuccessful() {
        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        boolean existsByCourse = this.studentRepository.existsByCourseId(null);

        Assertions.assertThat(existsByCourse).isTrue();
    }

    @Test
    @DisplayName("exists By Course Id returns false when successful")
    void existsByCourseId_Returnsfalse_WhenSuccessful() {
        UUID randomId = UUID.randomUUID();

        StudentModel newStudent = createStudent();
        StudentModel savedStudent = this.studentRepository.save(newStudent);

        boolean existsByCourse = this.studentRepository.existsByCourseId(randomId);

        Assertions.assertThat(existsByCourse).isFalse();
        Assertions.assertThat(randomId).isNotNull();
        Assertions.assertThat(savedStudent.getCourse()).isNull();
    }


    private StudentModel createStudent(){
        return StudentModel.builder()
                .name("Student test Name")
                .email("Student test Email")
                .phone("Student test Phone")
                .gender("T")
                .course(null)
                .birthDate(LocalDate.parse("2005-12-12"))
                .build();
    }

    private List<StudentModel> createStudentsList(Integer amount){
        List<StudentModel> StudentModels = new ArrayList<>();

        for (int i = 0; i <= amount; i++){
            StudentModels.add(StudentModel.builder()
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

        return StudentModels;
    }
}