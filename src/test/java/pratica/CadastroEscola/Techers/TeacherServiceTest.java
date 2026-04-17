package pratica.CadastroEscola.Techers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;
import pratica.CadastroEscola.TestUtils.TeacherCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    int numberOfElements = 20;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Page<TeacherModel> teacherPage = new PageImpl<>(TeacherCreator.createValidTeachersList(numberOfElements));

        BDDMockito.when(teacherRepository.findAll())
                .thenReturn(TeacherCreator.createValidTeachersList(numberOfElements));

        BDDMockito.when(teacherRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(teacherPage);

        BDDMockito.when(teacherRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(TeacherCreator.createValidTeacher()));

        BDDMockito.when(teacherRepository.existsById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(true);

        BDDMockito.when(teacherRepository.save(ArgumentMatchers.any(TeacherModel.class)))
                .thenReturn(TeacherCreator.createValidTeacher());

        BDDMockito.when(teacherRepository.existsByEmail(ArgumentMatchers.any(String.class)))
                .thenReturn(false);

        BDDMockito.doNothing().when(teacherRepository).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("getAllPaged returns a List of TeacherResponseDTO inside a Page when successful")
    void getAllPaged_ReturnsListOfTeacherResponseDTOsInsidePage_WhenSuccessful() {

        List<TeacherResponseDTO> expectedList = TeacherCreator.createTeacherResponseDTOList(numberOfElements);

        Page<TeacherResponseDTO> controllerResponse = teacherService.getAllPaged(PageRequest.of(1, 1));


        Assertions.assertThat(controllerResponse).isNotEmpty();

        Assertions.assertThat(controllerResponse.getTotalElements())
                .isEqualTo(expectedList.size());
    }

    @Test
    @DisplayName("getAll returns a List of TeacherResponseDTO when successful")
    void getAll_ReturnsListOfTeacherResponseDTOs_WhenSuccessful() {

        List<TeacherResponseDTO> expectedList = TeacherCreator.createTeacherResponseDTOList(numberOfElements);

        List<TeacherResponseDTO> controllerResponse = teacherService.getAll();


        Assertions.assertThat(controllerResponse)
                .isNotEmpty()
                .hasSize(expectedList.size());
    }

    @Test
    @DisplayName("getById returns a TeacherResponseDTO when successful")
    void getById_ReturnsTeacherResponseDTO_WhenSuccessful() {

        TeacherResponseDTO expectedTeacher = TeacherCreator.createTeacherResponseDTO();

        TeacherResponseDTO controllerResponse = teacherService.getById(UUID.randomUUID());

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
    void post_ReturnsTeacherResponseDTO_WhenSuccessful() {

        TeacherResponseDTO expectedTeacher = TeacherCreator.createTeacherResponseDTO();

        TeacherResponseDTO controllerResponse = teacherService.post(TeacherCreator.createTeacherRequestDTO());

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
    void delete_RemovesTeacher_WhenSuccessful() {

        Assertions.assertThatCode(() -> teacherService.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete Throws a ResourceNotFoundException when Teacher not found")
    void delete_ThrowsResourceNotFoundException_WhenTeacherNotFound() {

        BDDMockito.when(teacherRepository.existsById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(false);

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> teacherService.deleteById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("patch returns a TeacherResponseDTO when successful")
    void patch_ReturnsTeacherResponseDTO_WhenSuccessful() {

        TeacherResponseDTO expectedTeacher = TeacherCreator.createTeacherResponseDTO();

        TeacherResponseDTO controllerResponse = teacherService.patchById(UUID.randomUUID(),
                TeacherCreator.createTeacherRequestDTO());

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
    @DisplayName("validateTeacherDTO Throws BadRequestException When TeacherDTO is Empty")
    void validateTeacherDTO_ThrowsBadRequestException_WhenTeacherDtoIsEmpty() {
        TeacherDTO nullTeacherDTO = new TeacherDTO();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> teacherService.validateTeacherDTO(nullTeacherDTO))
                .withMessage("corpo da requisição não pode estar vazia");
    }

    @Test
    @DisplayName("emailBeingUsed Throws BadRequestException Email already exists")
    void validateTeacherDTO_ThrowsBadRequestException_WhenEmailExists() {

        BDDMockito.when(teacherRepository.existsByEmail(ArgumentMatchers.any(String.class)))
                .thenReturn(true);

        TeacherDTO teacherRequestDTO = TeacherCreator.createTeacherRequestDTO();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> teacherService.emailBeingUsedTeacher(teacherRequestDTO.getEmail()));
    }
}