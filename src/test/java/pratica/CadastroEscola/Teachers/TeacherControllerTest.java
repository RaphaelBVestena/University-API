package pratica.CadastroEscola.Teachers;

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
import pratica.CadastroEscola.TestUtils.TeacherCreator;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private TeacherService teacherServiceMock;

    int numberOfElements = 20;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        Page<TeacherResponseDTO> teachersPage = new PageImpl<>(
                TeacherCreator.createTeacherResponseDTOList(numberOfElements));

        BDDMockito.when(teacherServiceMock.getAllPaged(ArgumentMatchers.any()))
                .thenReturn(teachersPage);

        BDDMockito.when(teacherServiceMock.getAll())
                .thenReturn(TeacherCreator.createTeacherResponseDTOList(numberOfElements));

        BDDMockito.when(teacherServiceMock.getById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(TeacherCreator.createTeacherResponseDTO());

        BDDMockito.when(teacherServiceMock.getById(ArgumentMatchers.any()))
                .thenReturn(TeacherCreator.createTeacherResponseDTO());

        BDDMockito.when(teacherServiceMock.post(ArgumentMatchers.any(TeacherDTO.class)))
                .thenReturn(TeacherCreator.createTeacherResponseDTO());

        BDDMockito.when(teacherServiceMock.patchById(ArgumentMatchers.any(UUID.class),
                                                     ArgumentMatchers.any(TeacherDTO.class)))
                .thenReturn(TeacherCreator.createTeacherResponseDTO());

        BDDMockito.doNothing().when(teacherServiceMock).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("getAllPaged returns a List of TeacherResponseDTO inside a Page when successful")
    void getAllPaged_ReturnsListOfTeacherResponseDTOsInsidePage_WhenSuccessful(){

        List<TeacherResponseDTO> expectedList = TeacherCreator.createTeacherResponseDTOList(numberOfElements);

        Page<TeacherResponseDTO> controllerResponse = teacherController.getAllPaged(null).getBody();


        Assertions.assertThat(controllerResponse).isNotEmpty();

        Assertions.assertThat(controllerResponse.getTotalElements())
            .isEqualTo(expectedList.size());
    }

    @Test
    @DisplayName("getAll returns a List of TeacherResponseDTO when successful")
    void getAll_ReturnsListOfTeacherResponseDTOs_WhenSuccessful(){

        List<TeacherResponseDTO> expectedList = TeacherCreator.createTeacherResponseDTOList(numberOfElements);

        List<TeacherResponseDTO> controllerResponse = teacherController.getAll().getBody();


        Assertions.assertThat(controllerResponse)
                .isNotEmpty()
                .hasSize(expectedList.size());

    }

    @Test
    @DisplayName("getById returns a TeacherResponseDTO when successful")
    void getById_ReturnsTeacherResponseDTO_WhenSuccessful(){

        TeacherResponseDTO expectedTeacher = TeacherCreator.createTeacherResponseDTO();

        TeacherResponseDTO controllerResponse = teacherController.getById(UUID.randomUUID()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedTeacher.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedTeacher.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedTeacher.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedTeacher.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedTeacher.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("post returns a TeacherResponseDTO when successful")
    void post_ReturnsTeacherResponseDTO_WhenSuccessful(){

        TeacherResponseDTO expectedTeacher = TeacherCreator.createTeacherResponseDTO();

        TeacherResponseDTO controllerResponse = teacherController.post(TeacherCreator.createTeacherRequestDTO()).getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedTeacher.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedTeacher.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedTeacher.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedTeacher.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedTeacher.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("delete removes a Teacher when successful")
    void delete_RemovesTeacher_WhenSuccessful(){

        Assertions.assertThatCode(() -> teacherController.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> controllerResponse = teacherController.deleteById(UUID.randomUUID());

        Assertions.assertThat(controllerResponse).isNotNull();
        Assertions.assertThat(controllerResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("patch returns a TeacherResponseDTO when successful")
    void patch_ReturnsTeacherResponseDTO_WhenSuccessful(){

        TeacherResponseDTO expectedTeacher = TeacherCreator.createTeacherResponseDTO();

        TeacherResponseDTO controllerResponse = teacherController.patchById(UUID.randomUUID(),
                                                                  TeacherCreator.createTeacherRequestDTO())
                        .getBody();

        Assertions.assertThat(controllerResponse).isNotNull();

        Assertions.assertThat(controllerResponse.getName()).isEqualTo(expectedTeacher.getName());
        Assertions.assertThat(controllerResponse.getEmail()).isEqualTo(expectedTeacher.getEmail());
        Assertions.assertThat(controllerResponse.getPhone()).isEqualTo(expectedTeacher.getPhone());
        Assertions.assertThat(controllerResponse.getGender()).isEqualTo(expectedTeacher.getGender());
        Assertions.assertThat(controllerResponse.getBirthDate()).isEqualTo(expectedTeacher.getBirthDate());

        Assertions.assertThat(controllerResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(controllerResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }
}