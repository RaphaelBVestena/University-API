package pratica.CadastroEscola.Courses;

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
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Save creates a new course when successful")
    public void save_PersistCourse_WhenSuccessful(){

        CourseModel newCourse = createCourse();
        CourseModel savedCourse = this.courseRepository.save(newCourse);

        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates the course when successful")
    public void save_UpdateCourse_WhenSuccessful(){

        CourseModel newCourse = createCourse();
        CourseModel savedCourse = this.courseRepository.save(newCourse);

        savedCourse.setName("Updated Course test name");

        CourseModel updatedCourse = this.courseRepository.save(savedCourse);

        Assertions.assertThat(updatedCourse).isNotNull();
        Assertions.assertThat(updatedCourse.getId()).isNotNull();

        Assertions.assertThat(updatedCourse.getId()).isEqualTo(savedCourse.getId());
        Assertions.assertThat(updatedCourse.getName()).isEqualTo(savedCourse.getName());
        Assertions.assertThat(updatedCourse.getTeacher()).isEqualTo(savedCourse.getTeacher());
        Assertions.assertThat(updatedCourse.getSemester()).isEqualTo(savedCourse.getSemester());
    }

    @Test
    @DisplayName("Delete removes course when successful")
    public void delete_RemovesCourse_WhenSuccessful(){

        CourseModel newCourse = createCourse();
        CourseModel savedCourse = this.courseRepository.save(newCourse);

        this.courseRepository.deleteById(savedCourse.getId());

        Optional<CourseModel> courseOptional = this.courseRepository.findById(savedCourse.getId());

        Assertions.assertThat(courseOptional).isEmpty();
    }

    @Test
    @DisplayName("FindAll returns 2 pages with content when successful" )
    public void findAll_returns2PagesWithContent_WhenSuccessful(){
        int amount = 10;

        this.courseRepository.saveAll(createCoursesList(amount));

        Page<CourseModel> coursesPage1 = this.courseRepository.findAll(PageRequest.of(0, amount/2));
        Page<CourseModel> coursesPage2 = this.courseRepository.findAll(PageRequest.of(1, amount/2));

        Assertions.assertThat(coursesPage1).isNotEmpty();
        Assertions.assertThat(coursesPage2).isNotEmpty();

        Assertions.assertThat(coursesPage1.getNumberOfElements()).isEqualTo(amount/2);
        Assertions.assertThat(coursesPage2.getNumberOfElements()).isEqualTo(amount/2);

        Assertions.assertThat(coursesPage1.getNumber()).isEqualTo(0);
        Assertions.assertThat(coursesPage2.getNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("FindAll returns a empty page when successful" )
    public void findAll_ReturnsEmptyPage_WhenSuccessful(){

        Page<CourseModel> coursesPage = this.courseRepository.findAll(PageRequest.of(0, 20));

        Assertions.assertThat(coursesPage).isNotNull();
        Assertions.assertThat(coursesPage.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Exists By Name returns true when successful" )
    public void existsByName_ReturnsTrue_WhenSuccessful(){

        CourseModel newCourse = createCourse();
        CourseModel savedCourse = this.courseRepository.save(newCourse);

        boolean existsByName = this.courseRepository.existsByName(newCourse.getName());

        Assertions.assertThat(existsByName).isTrue();
        Assertions.assertThat(savedCourse.getName()).isNotNull();
        Assertions.assertThat(newCourse.getName()).isNotNull();
    }

    @Test
    @DisplayName("Exists By Name returns False when successful" )
    public void existsByName_ReturnsFalse_WhenSuccessful(){

        CourseModel newCourse = createCourse();
        CourseModel savedCourse = this.courseRepository.save(newCourse);

        boolean existsByName = this.courseRepository.existsByName("non-existent Course Name");

        Assertions.assertThat(existsByName).isFalse();
        Assertions.assertThat(savedCourse.getName()).isNotNull();
        Assertions.assertThat(newCourse.getName()).isNotNull();
    }


    private CourseModel createCourse(){
        return CourseModel.builder()
                .name("Course test Name")
                .teacher(null)
                .semester(8)
                .build();
    }

    private List<CourseModel> createCoursesList(Integer amount){
        List<CourseModel> courseModels = new ArrayList<>();

        for (int i = 0; i <= amount; i++){
            courseModels.add(CourseModel.builder()
                    .id(null)
                    .name(String.valueOf(i))
                    .teacher(null)
                    .semester(8)
                    .creationTime(null)
                    .updateTime(null)
                    .build());
        }

        return courseModels;
    }
}