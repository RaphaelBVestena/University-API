package pratica.CadastroEscola.Courses;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pratica.CadastroEscola.Students.StudentModel;
import pratica.CadastroEscola.Students.StudentRepository;
import pratica.CadastroEscola.Teachers.TeacherModel;
import pratica.CadastroEscola.Teachers.TeacherRepository;
import pratica.CadastroEscola.TestUtils.CourseCreator;
import pratica.CadastroEscola.TestUtils.StudentCreator;
import pratica.CadastroEscola.TestUtils.TeacherCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    
    //  SAVE
    
    @Test
    @DisplayName("Save creates a new course with teacher when successful")
    public void save_PersistCourseWithTeacher_WhenSuccessful(){

        TeacherModel existentTeacher = this.teacherRepository.save(
                TeacherCreator.createToBeSavedTeacher()
        );

        CourseModel courseToBeSaved =
                CourseCreator.createToBeSavedCourseWithTeacher(existentTeacher);

        CourseModel savedCourse = this.courseRepository.save(courseToBeSaved);

        this.courseRepository.flush();

        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getId()).isNotNull();
        Assertions.assertThat(savedCourse.getTeacher()).isEqualTo(existentTeacher);
    }

    @Test
    @DisplayName("Save creates a new course with no teacher when successful")
    public void save_PersistCourseWithNoTeacher_WhenSuccessful(){

        CourseModel courseToBeSaved =
                CourseCreator.createToBeSavedCourse();

        CourseModel savedCourse = this.courseRepository.save(courseToBeSaved);

        this.courseRepository.flush();

        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getId()).isNotNull();
        Assertions.assertThat(savedCourse.getTeacher()).isNull();
    }

    @Test
    @DisplayName("Save updates the course when successful")
    public void save_UpdateCourse_WhenSuccessful(){

        TeacherModel existentTeacher = this.teacherRepository.save(
                TeacherCreator.createToBeSavedTeacher()
        );

        CourseModel courseToBeSaved = CourseCreator.createToBeSavedCourseWithTeacher(existentTeacher);

        CourseModel savedCourse = this.courseRepository.save(courseToBeSaved);

        savedCourse.setName("Updated Course test name");

        CourseModel reloadedCourse = this.courseRepository.findById(savedCourse.getId()).get();

        this.courseRepository.flush();

        Assertions.assertThat(reloadedCourse).isNotNull();
        Assertions.assertThat(reloadedCourse.getId()).isNotNull();

        Assertions.assertThat(reloadedCourse.getId()).isEqualTo(savedCourse.getId());
        Assertions.assertThat(reloadedCourse.getName()).isEqualTo("Updated Course test name");
        Assertions.assertThat(reloadedCourse.getTeacher()).isEqualTo(existentTeacher);
    }

    @Test
    @DisplayName("SaveAll saves a course list when successful")
    public void saveAll_PersistCourseList_WhenSuccessful(){
        int amount = 10;
        
        List<CourseModel> toBeSavedCourseList = CourseCreator.createToBeSavedCourseList(amount);
        
        List<CourseModel> savedCourseList = this.courseRepository.saveAll(toBeSavedCourseList);

        this.courseRepository.flush();

        Assertions.assertThat(savedCourseList)
                .isNotEmpty()
                .hasSize(amount);
    }

    
    // DELETE
    
    @Test
    @DisplayName("Delete removes course when successful")
    public void delete_RemovesCourse_WhenSuccessful(){

        CourseModel savedCourse = this.courseRepository.save(
                CourseCreator.createToBeSavedCourse()
        );

        this.courseRepository.deleteById(savedCourse.getId());

        Optional<CourseModel> courseOptional = this.courseRepository.findById(savedCourse.getId());

        Assertions.assertThat(courseOptional).isEmpty();
    }


//    @Test
//    @DisplayName("Delete Throws Exception when course has students")
//    public void delete_ThrowsException_WhenCourseHasStudents(){
//
//        CourseModel savedCourse = courseRepository.saveAndFlush(
//                CourseCreator.createToBeSavedCourse()
//        );
//
//        StudentModel student = StudentCreator.createToBeSavedStudentWithCourse(savedCourse);
//
//        studentRepository.save(student);
//
//        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
//                .isThrownBy(() -> {
//                    courseRepository.deleteById(savedCourse.getId());
//                    courseRepository.flush();
//                });
//    }

    
    //EXISTS BY NAME
    
    @Test
    @DisplayName("Exists By Name returns True when successful" )
    public void existsByName_ReturnsTrue_WhenSuccessful(){

        CourseModel courseToBeSaved = CourseCreator.createToBeSavedCourse();
        CourseModel savedCourse = this.courseRepository.saveAndFlush(courseToBeSaved);

        boolean existsByName = this.courseRepository.existsByName(savedCourse.getName());

        Assertions.assertThat(existsByName).isTrue();
        Assertions.assertThat(savedCourse.getName()).isNotEmpty();
    }

    @Test
    @DisplayName("Exists By Name returns False when successful" )
    public void existsByName_ReturnsFalse_WhenSuccessful(){

        CourseModel courseToBeSaved = CourseCreator.createToBeSavedCourse();
        CourseModel savedCourse = this.courseRepository.saveAndFlush(courseToBeSaved);

        boolean existsByName = this.courseRepository.existsByName("non-existent name");

        Assertions.assertThat(existsByName).isFalse();
        Assertions.assertThat(savedCourse.getName()).isNotEmpty();
    }

    
    //FIND BY ID
    
    @Test
    @DisplayName("FindById returns course when found")
    void findById_ReturnsCourse_WhenSuccessful() {
        CourseModel savedCourse = courseRepository.save(CourseCreator.createToBeSavedCourse());

        Optional<CourseModel> response = courseRepository.findById(savedCourse.getId());

        Assertions.assertThat(response).isPresent();
    }

    @Test
    @DisplayName("FindById returns empty when not found")
    void findById_ReturnsEmpty_WhenNotFound() {
        Optional<CourseModel> response = courseRepository.findById(UUID.randomUUID());

        Assertions.assertThat(response).isEmpty();
    }

    
    //FIND ALL

    @Test
    @DisplayName("FindAll returns a Courses List when successful" )
    public void findAll_ReturnsCoursesList_WhenSuccessful(){
        int amount = 10;

        List<CourseModel> courseList = CourseCreator.createToBeSavedCourseList(amount);

        this.courseRepository.saveAll(courseList);

        List<CourseModel> resultList = this.courseRepository.findAll();

        Assertions.assertThat(resultList).isNotEmpty();
        Assertions.assertThat(resultList.size()).isEqualTo(amount);
    }

    @Test
    @DisplayName("FindAll returns empty List when successful" )
    public void findAll_ReturnsEmptyList_WhenSuccessful(){

        List<CourseModel> resultList = this.courseRepository.findAll();

        Assertions.assertThat(resultList).isEmpty();
        Assertions.assertThat(resultList).isNotNull();
    }
    
    @Test
    @DisplayName("FindAll returns 2 pages with content when successful" )
    public void findAll_returns2PagesWithContent_WhenSuccessful(){
        int amount = 10;

        this.courseRepository.saveAll(CourseCreator.createToBeSavedCourseList(amount));

        Page<CourseModel> coursesPage1 = this.courseRepository.findAll(PageRequest.of(0, amount/2));
        Page<CourseModel> coursesPage2 = this.courseRepository.findAll(PageRequest.of(1, amount/2));

        Assertions.assertThat(coursesPage1).isNotEmpty();
        Assertions.assertThat(coursesPage2).isNotEmpty();

        Assertions.assertThat(coursesPage1.getNumberOfElements()).isEqualTo(amount/2);
        Assertions.assertThat(coursesPage2.getNumberOfElements()).isEqualTo(amount/2);

        Assertions.assertThat(coursesPage1.getNumber()).isEqualTo(0);
        Assertions.assertThat(coursesPage2.getNumber()).isEqualTo(1);

        Assertions.assertThat(coursesPage1.isFirst()).isTrue();
        Assertions.assertThat(coursesPage2.isLast()).isTrue();
    }

    @Test
    @DisplayName("FindAll returns a empty page when successful" )
    public void findAll_ReturnsEmptyPage_WhenSuccessful(){

        Page<CourseModel> coursesPage = this.courseRepository.findAll(PageRequest.of(0, 20));

        Assertions.assertThat(coursesPage).isNotNull();
        Assertions.assertThat(coursesPage.getContent()).isEmpty();
    }
}   