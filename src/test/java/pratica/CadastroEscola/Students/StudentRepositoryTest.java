package pratica.CadastroEscola.Students;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pratica.CadastroEscola.Courses.CourseModel;
import pratica.CadastroEscola.Courses.CourseRepository;
import pratica.CadastroEscola.TestUtils.StudentCreator;
import pratica.CadastroEscola.TestUtils.CourseCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    //  SAVE

    @Test
    @DisplayName("Save creates a new student with course when successful")
    public void save_PersistStudentWithCourse_WhenSuccessful(){

        CourseModel existentCourse = this.courseRepository.save(
                CourseCreator.createToBeSavedCourse()
        );

        StudentModel studentToBeSaved =
                StudentCreator.createToBeSavedStudentWithCourse(existentCourse);

        StudentModel savedStudent = this.studentRepository.save(studentToBeSaved);

        this.studentRepository.flush();

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isNotNull();
        Assertions.assertThat(savedStudent.getCourse()).isEqualTo(existentCourse);
    }

    @Test
    @DisplayName("Save creates a new student with no course when successful")
    public void save_PersistStudentWithNoCourse_WhenSuccessful(){

        StudentModel studentToBeSaved =
                StudentCreator.createToBeSavedStudent();

        StudentModel savedStudent = this.studentRepository.save(studentToBeSaved);

        this.studentRepository.flush();

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isNotNull();
        Assertions.assertThat(savedStudent.getCourse()).isNull();
    }

    @Test
    @DisplayName("Save updates the student when successful")
    public void save_UpdateStudent_WhenSuccessful(){

        CourseModel existentCourse = this.courseRepository.save(
                CourseCreator.createToBeSavedCourse()
        );

        StudentModel studentToBeSaved = StudentCreator.createToBeSavedStudentWithCourse(existentCourse);

        StudentModel savedStudent = this.studentRepository.save(studentToBeSaved);

        savedStudent.setName("Updated Student test name");

        StudentModel reloadedStudent = this.studentRepository.findById(savedStudent.getId()).get();

        this.studentRepository.flush();

        Assertions.assertThat(reloadedStudent).isNotNull();
        Assertions.assertThat(reloadedStudent.getId()).isNotNull();

        Assertions.assertThat(reloadedStudent.getId()).isEqualTo(savedStudent.getId());
        Assertions.assertThat(reloadedStudent.getName()).isEqualTo("Updated Student test name");
        Assertions.assertThat(reloadedStudent.getCourse()).isEqualTo(existentCourse);
    }

    @Test
    @DisplayName("SaveAll saves a student list when successful")
    public void saveAll_PersistStudentList_WhenSuccessful(){
        int amount = 10;

        List<StudentModel> toBeSavedStudentList = StudentCreator.createToBeSavedStudentList(amount);

        List<StudentModel> savedStudentList = this.studentRepository.saveAll(toBeSavedStudentList);

        this.studentRepository.flush();

        Assertions.assertThat(savedStudentList)
                .isNotEmpty()
                .hasSize(amount);
    }


    // DELETE

    @Test
    @DisplayName("Delete removes student when successful")
    public void delete_RemovesStudent_WhenSuccessful(){

        StudentModel studentToBeSaved = StudentCreator.createToBeSavedStudent();
        StudentModel savedStudent = this.studentRepository.save(studentToBeSaved);

        this.studentRepository.deleteById(savedStudent.getId());

        Optional<StudentModel> studentOptional = this.studentRepository.findById(savedStudent.getId());

        Assertions.assertThat(studentOptional).isEmpty();
    }


    //EXISTS BY EMAIL

    @Test
    @DisplayName("Exists By Email returns True when successful" )
    public void existsByEmail_ReturnsTrue_WhenSuccessful(){

        StudentModel studentToBeSaved = StudentCreator.createToBeSavedStudent();
        StudentModel savedStudent = this.studentRepository.saveAndFlush(studentToBeSaved);

        boolean existsByEmail = this.studentRepository.existsByEmail(savedStudent.getEmail());

        Assertions.assertThat(existsByEmail).isTrue();
        Assertions.assertThat(savedStudent.getEmail()).isNotEmpty();
    }

    @Test
    @DisplayName("Exists By Email returns False when successful" )
    public void existsByEmail_ReturnsFalse_WhenSuccessful(){

        StudentModel studentToBeSaved = StudentCreator.createToBeSavedStudent();
        StudentModel savedStudent = this.studentRepository.saveAndFlush(studentToBeSaved);

        boolean existsByEmail = this.studentRepository.existsByEmail("non-existent name");

        Assertions.assertThat(existsByEmail).isFalse();
        Assertions.assertThat(savedStudent.getEmail()).isNotEmpty();
    }


    //EXISTS BY COURSE ID

    @Test
    @DisplayName("Exists By CourseId returns True when successful" )
    public void existsByCourseId_ReturnsTrue_WhenSuccessful(){

        CourseModel existentCourse = this.courseRepository.save(
                CourseCreator.createToBeSavedCourse()
        );

        StudentModel toBeSavedStudent =
                StudentCreator.createToBeSavedStudentWithCourse(existentCourse);

        StudentModel savedStudent = this.studentRepository.save(toBeSavedStudent);

        this.studentRepository.flush();

        boolean studentExistsByCourseId = this.studentRepository.existsByCourseId(
                savedStudent.getCourse().getId());

        Assertions.assertThat(studentExistsByCourseId).isTrue();
        Assertions.assertThat(savedStudent.getId()).isNotNull();
        Assertions.assertThat(savedStudent.getCourse()).isEqualTo(existentCourse);

    }

    @Test
    @DisplayName("Exists By CourseId returns False when successful" )
    public void existsByCourseId_ReturnsFalse_WhenSuccessful(){

        CourseModel existentCourse = this.courseRepository.save(
                CourseCreator.createToBeSavedCourse()
        );

        StudentModel toBeSavedStudent =
                StudentCreator.createToBeSavedStudentWithCourse(existentCourse);

        StudentModel savedStudent = this.studentRepository.save(toBeSavedStudent);

        this.studentRepository.flush();

        boolean studentExistsByCourseId = this.studentRepository.existsByCourseId(UUID.randomUUID());

        Assertions.assertThat(studentExistsByCourseId).isFalse();
        Assertions.assertThat(savedStudent.getId()).isNotNull();
        Assertions.assertThat(savedStudent.getCourse()).isEqualTo(existentCourse);
    }


    //FIND BY ID

    @Test
    @DisplayName("FindById returns student when found")
    void findById_ReturnsStudent_WhenSuccessful() {
        StudentModel savedStudent = studentRepository.save(StudentCreator.createToBeSavedStudent());

        Optional<StudentModel> response = studentRepository.findById(savedStudent.getId());

        Assertions.assertThat(response).isPresent();
    }

    @Test
    @DisplayName("FindById returns empty when not found")
    void findById_ReturnsEmpty_WhenNotFound() {
        Optional<StudentModel> response = studentRepository.findById(UUID.randomUUID());

        Assertions.assertThat(response).isEmpty();
    }


    //FIND ALL

    @Test
    @DisplayName("FindAll returns a Students List when successful" )
    public void findAll_ReturnsStudentsList_WhenSuccessful(){
        int amount = 10;

        List<StudentModel> studentList = StudentCreator.createToBeSavedStudentList(amount);

        this.studentRepository.saveAll(studentList);

        List<StudentModel> resultList = this.studentRepository.findAll();

        Assertions.assertThat(resultList).isNotEmpty();
        Assertions.assertThat(resultList.size()).isEqualTo(amount);
    }

    @Test
    @DisplayName("FindAll returns empty List when successful" )
    public void findAll_ReturnsEmptyList_WhenSuccessful(){

        List<StudentModel> resultList = this.studentRepository.findAll();

        Assertions.assertThat(resultList).isEmpty();
        Assertions.assertThat(resultList).isNotNull();
    }

    @Test
    @DisplayName("FindAll returns 2 pages with content when successful" )
    public void findAll_returns2PagesWithContent_WhenSuccessful(){
        int amount = 10;

        this.studentRepository.saveAll(StudentCreator.createToBeSavedStudentList(amount));

        Page<StudentModel> studentsPage1 = this.studentRepository.findAll(PageRequest.of(0, amount/2));
        Page<StudentModel> studentsPage2 = this.studentRepository.findAll(PageRequest.of(1, amount/2));

        Assertions.assertThat(studentsPage1).isNotEmpty();
        Assertions.assertThat(studentsPage2).isNotEmpty();

        Assertions.assertThat(studentsPage1.getNumberOfElements()).isEqualTo(amount/2);
        Assertions.assertThat(studentsPage2.getNumberOfElements()).isEqualTo(amount/2);

        Assertions.assertThat(studentsPage1.getNumber()).isEqualTo(0);
        Assertions.assertThat(studentsPage2.getNumber()).isEqualTo(1);

        Assertions.assertThat(studentsPage1.isFirst()).isTrue();
        Assertions.assertThat(studentsPage2.isLast()).isTrue();
    }

    @Test
    @DisplayName("FindAll returns a empty page when successful" )
    public void findAll_ReturnsEmptyPage_WhenSuccessful(){

        Page<StudentModel> studentsPage = this.studentRepository.findAll(PageRequest.of(0, 20));

        Assertions.assertThat(studentsPage).isNotNull();
        Assertions.assertThat(studentsPage.getContent()).isEmpty();
    }
}