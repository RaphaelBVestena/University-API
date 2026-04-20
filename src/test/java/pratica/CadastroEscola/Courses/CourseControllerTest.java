package pratica.CadastroEscola.Courses;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pratica.CadastroEscola.TestUtils.CourseCreator;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseServiceMock;

    int numberOfElements = 20;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        Page<CourseResponseDTO> coursesPage = new PageImpl<>(
                CourseCreator.createCourseResponseDTOList(numberOfElements));

        BDDMockito.when(courseServiceMock.getAllPaged(ArgumentMatchers.any()))
                .thenReturn(coursesPage);

        BDDMockito.when(courseServiceMock.getAll())
                .thenReturn(CourseCreator.createCourseResponseDTOList(numberOfElements));

        BDDMockito.when(courseServiceMock.getById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(CourseCreator.createCourseResponseDTO());

        BDDMockito.when(courseServiceMock.post(ArgumentMatchers.any(CourseDTO.class)))
                .thenReturn(CourseCreator.createCourseResponseDTO());

        BDDMockito.when(courseServiceMock.patchById(ArgumentMatchers.any(UUID.class),
                        ArgumentMatchers.any(CourseDTO.class)))
                .thenReturn(CourseCreator.createCourseResponseDTO());

        BDDMockito.doNothing().when(courseServiceMock).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("getAllPaged returns a List of CourseResponseDTO inside a Page when successful")
    void getAllPaged_ReturnsListOfCourseResponseDTOsInsidePage_WhenSuccessful(){

        List<CourseResponseDTO> expectedList = CourseCreator.createCourseResponseDTOList(numberOfElements);

        Page<CourseResponseDTO> controllerResponse = courseController.getAllPaged(null).getBody();


        Assertions.assertThat(controllerResponse).isNotEmpty();

        Assertions.assertThat(controllerResponse.getTotalElements())
                .isEqualTo(expectedList.size());
    }

    @Test
    @DisplayName("getAll returns a List of CourseResponseDTO when successful")
    void getAll_ReturnsListOfCourseResponseDTOs_WhenSuccessful(){

        List<CourseResponseDTO> expectedList = CourseCreator.createCourseResponseDTOList(numberOfElements);

        List<CourseResponseDTO> controllerResponse = courseController.getAll().getBody();


        Assertions.assertThat(controllerResponse)
                .isNotEmpty()
                .hasSize(expectedList.size());

    }

    @Test
    @DisplayName("getById returns a CourseResponseDTO when successful")
    void getById_ReturnsCourseResponseDTO_WhenSuccessful(){

        CourseResponseDTO expectedCourse = CourseCreator.createCourseResponseDTO();

        CourseResponseDTO controllerResponse = courseController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(controllerResponse.getSemester()).isEqualTo(expectedCourse.getSemester());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("getById returns a CourseResponseDTO With Teacher when successful")
    void getById_ReturnsCourseResponseDTOWithTeacher_WhenSuccessful(){

        BDDMockito.when(courseServiceMock.getById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(CourseCreator.createCourseResponseDTOWithTeacher());

        CourseResponseDTO expectedCourse = CourseCreator.createCourseResponseDTOWithTeacher();

        CourseResponseDTO controllerResponse = courseController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getTeacher()).isInstanceOf(TeacherSummaryDTO.class);

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(controllerResponse.getSemester()).isEqualTo(expectedCourse.getSemester());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();
        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("post returns a CourseResponseDTO when successful")
    void post_ReturnsCourseResponseDTO_WhenSuccessful(){

        CourseResponseDTO expectedCourse = CourseCreator.createCourseResponseDTO();

        CourseResponseDTO controllerResponse = courseController.post(CourseCreator.createCourseRequestDTO()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(controllerResponse.getSemester()).isEqualTo(expectedCourse.getSemester());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("delete removes a Course when successful")
    void delete_RemovesCourse_WhenSuccessful(){

        Assertions.assertThatCode(() -> courseController.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> controllerResponse = courseController.deleteById(UUID.randomUUID());

        Assertions.assertThat(controllerResponse).isNotNull();
        Assertions.assertThat(controllerResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("patch returns a CourseResponseDTO when successful")
    void patch_ReturnsCourseResponseDTO_WhenSuccessful(){

        CourseResponseDTO expectedCourse = CourseCreator.createCourseResponseDTO();

        CourseResponseDTO controllerResponse = courseController.patchById(UUID.randomUUID(),
                        CourseCreator.createCourseRequestDTO())
                .getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(controllerResponse.getSemester()).isEqualTo(expectedCourse.getSemester());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }
}