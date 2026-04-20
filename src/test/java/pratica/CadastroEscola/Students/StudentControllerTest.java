package pratica.CadastroEscola.Students;

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
import pratica.CadastroEscola.Courses.CourseResponseDTO;
import pratica.CadastroEscola.Courses.TeacherSummaryDTO;
import pratica.CadastroEscola.Students.StudentController;
import pratica.CadastroEscola.Students.StudentDTO;
import pratica.CadastroEscola.Students.StudentResponseDTO;
import pratica.CadastroEscola.Students.StudentService;
import pratica.CadastroEscola.TestUtils.CourseCreator;
import pratica.CadastroEscola.TestUtils.StudentCreator;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentServiceMock;

    int numberOfElements = 20;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        Page<StudentResponseDTO> studentsPage = new PageImpl<>(
                StudentCreator.createStudentResponseDTOList(numberOfElements));

        BDDMockito.when(studentServiceMock.getAllPaged(ArgumentMatchers.any()))
                .thenReturn(studentsPage);

        BDDMockito.when(studentServiceMock.getAll())
                .thenReturn(StudentCreator.createStudentResponseDTOList(numberOfElements));

        BDDMockito.when(studentServiceMock.getById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(StudentCreator.createStudentResponseDTO());

        BDDMockito.when(studentServiceMock.getById(ArgumentMatchers.any()))
                .thenReturn(StudentCreator.createStudentResponseDTO());

        BDDMockito.when(studentServiceMock.post(ArgumentMatchers.any(StudentDTO.class)))
                .thenReturn(StudentCreator.createStudentResponseDTO());

        BDDMockito.when(studentServiceMock.patchById(ArgumentMatchers.any(UUID.class),
                        ArgumentMatchers.any(StudentDTO.class)))
                .thenReturn(StudentCreator.createStudentResponseDTO());

        BDDMockito.doNothing().when(studentServiceMock).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("getAllPaged returns a List of StudentResponseDTO inside a Page when successful")
    void getAllPaged_ReturnsListOfStudentResponseDTOsInsidePage_WhenSuccessful(){

        List<StudentResponseDTO> expectedList = StudentCreator.createStudentResponseDTOList(numberOfElements);

        Page<StudentResponseDTO> controllerResponse = studentController.getAllPaged(null).getBody();


        Assertions.assertThat(controllerResponse).isNotEmpty();

        Assertions.assertThat(controllerResponse.getTotalElements())
                .isEqualTo(expectedList.size());
    }

    @Test
    @DisplayName("getAll returns a List of StudentResponseDTO when successful")
    void getAll_ReturnsListOfStudentResponseDTOs_WhenSuccessful(){

        List<StudentResponseDTO> expectedList = StudentCreator.createStudentResponseDTOList(numberOfElements);

        List<StudentResponseDTO> controllerResponse = studentController.getAll().getBody();


        Assertions.assertThat(controllerResponse)
                .isNotEmpty()
                .hasSize(expectedList.size());

    }

    @Test
    @DisplayName("getById returns a StudentResponseDTO when successful")
    void getById_ReturnsStudentResponseDTO_WhenSuccessful(){

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTO();

        StudentResponseDTO controllerResponse = studentController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("getById returns a StudentResponseDTO With Course when successful")
    void getById_ReturnsStudentResponseDTOWithCourse_WhenSuccessful(){

        BDDMockito.when(studentServiceMock.getById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(StudentCreator.createStudentResponseDTOWithCourse());

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTOWithCourse();

        StudentResponseDTO controllerResponse = studentController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getCourse()).isInstanceOf(CourseSummaryDTO.class);

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();
        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("post returns a StudentResponseDTO when successful")
    void post_ReturnsStudentResponseDTO_WhenSuccessful(){

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTO();

        StudentResponseDTO controllerResponse = studentController.post(StudentCreator.createStudentRequestDTO()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("delete removes a Student when successful")
    void delete_RemovesStudent_WhenSuccessful(){

        Assertions.assertThatCode(() -> studentController.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> controllerResponse = studentController.deleteById(UUID.randomUUID());

        Assertions.assertThat(controllerResponse).isNotNull();
        Assertions.assertThat(controllerResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("patch returns a StudentResponseDTO when successful")
    void patch_ReturnsStudentResponseDTO_WhenSuccessful(){

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTO();

        StudentResponseDTO controllerResponse = studentController.patchById(UUID.randomUUID(),
                        StudentCreator.createStudentRequestDTO())
                .getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }
}