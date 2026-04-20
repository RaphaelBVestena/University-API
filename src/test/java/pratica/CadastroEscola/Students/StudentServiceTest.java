package pratica.CadastroEscola.Students;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pratica.CadastroEscola.Courses.CourseRepository;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;
import pratica.CadastroEscola.Students.*;
import pratica.CadastroEscola.TestUtils.StudentCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    int numberOfElements = 20;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Page<StudentModel> studentPage = new PageImpl<>(StudentCreator.createValidStudentList(numberOfElements));

        BDDMockito.when(studentRepository.findAll())
                .thenReturn(StudentCreator.createValidStudentList(numberOfElements));

        BDDMockito.when(studentRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(studentPage);

        BDDMockito.when(studentRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(StudentCreator.createValidStudent()));

        BDDMockito.when(studentRepository.existsById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(true);

        BDDMockito.when(studentRepository.save(ArgumentMatchers.any(StudentModel.class)))
                .thenReturn(StudentCreator.createValidStudent());

        BDDMockito.when(studentRepository.existsByEmail(ArgumentMatchers.any(String.class)))
                .thenReturn(false);

        BDDMockito.doNothing().when(studentRepository).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("getAllPaged returns a List of StudentResponseDTO inside a Page when successful")
    void getAllPaged_ReturnsListOfStudentResponseDTOsInsidePage_WhenSuccessful() {

        List<StudentResponseDTO> expectedList = StudentCreator.createStudentResponseDTOList(numberOfElements);

        Page<StudentResponseDTO> serviceResponse = studentService.getAllPaged(PageRequest.of(1, 1));


        Assertions.assertThat(serviceResponse).isNotEmpty();

        Assertions.assertThat(serviceResponse.getTotalElements())
                .isEqualTo(expectedList.size());
    }

    @Test
    @DisplayName("getAll returns a List of StudentResponseDTO when successful")
    void getAll_ReturnsListOfStudentResponseDTOs_WhenSuccessful() {

        List<StudentResponseDTO> expectedList = StudentCreator.createStudentResponseDTOList(numberOfElements);

        List<StudentResponseDTO> serviceResponse = studentService.getAll();


        Assertions.assertThat(serviceResponse)
                .isNotEmpty()
                .hasSize(expectedList.size());
    }

    @Test
    @DisplayName("getById returns a StudentResponseDTO when successful")
    void getById_ReturnsStudentResponseDTO_WhenSuccessful() {

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTO();

        StudentResponseDTO serviceResponse = studentService.getById(UUID.randomUUID());

        Assertions.assertThat(serviceResponse).isNotNull();

        Assertions.assertThat(serviceResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(serviceResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(serviceResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(serviceResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(serviceResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(serviceResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(serviceResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("post returns a StudentResponseDTO when successful")
    void post_ReturnsStudentResponseDTO_WhenSuccessful() {

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTO();

        StudentResponseDTO serviceResponse = studentService.post(StudentCreator.createStudentRequestDTO());

        Assertions.assertThat(serviceResponse).isNotNull();

        Assertions.assertThat(serviceResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(serviceResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(serviceResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(serviceResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(serviceResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(serviceResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(serviceResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("delete removes a Student when successful")
    void delete_RemovesStudent_WhenSuccessful() {

        Assertions.assertThatCode(() -> studentService.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete Throws a ResourceNotFoundException when Student not found")
    void delete_ThrowsResourceNotFoundException_WhenStudentNotFound() {

        BDDMockito.when(studentRepository.existsById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(false);

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> studentService.deleteById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("patch returns a StudentResponseDTO when successful")
    void patch_ReturnsStudentResponseDTO_WhenSuccessful() {

        StudentResponseDTO expectedStudent = StudentCreator.createStudentResponseDTO();

        StudentResponseDTO serviceResponse = studentService.patchById(UUID.randomUUID(),
                StudentCreator.createStudentRequestDTO());

        Assertions.assertThat(serviceResponse).isNotNull();

        Assertions.assertThat(serviceResponse.getName()).isEqualTo(expectedStudent.getName());
        Assertions.assertThat(serviceResponse.getEmail()).isEqualTo(expectedStudent.getEmail());
        Assertions.assertThat(serviceResponse.getPhone()).isEqualTo(expectedStudent.getPhone());
        Assertions.assertThat(serviceResponse.getGender()).isEqualTo(expectedStudent.getGender());
        Assertions.assertThat(serviceResponse.getBirthDate()).isEqualTo(expectedStudent.getBirthDate());

        Assertions.assertThat(serviceResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(serviceResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("validateStudentDTO Throws BadRequestException When StudentDTO is Empty")
    void validateStudentDTO_ThrowsBadRequestException_WhenStudentDtoIsEmpty() {
        StudentDTO nullStudentDTO = new StudentDTO();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> studentService.validateStudentDTO(nullStudentDTO))
                .withMessage("corpo da requisição não pode estar vazia");
    }

    @Test
    @DisplayName("emailBeingUsed Throws BadRequestException when Email already exists")
    void emailBeingUsed_ThrowsBadRequestException_WhenEmailExists() {

        BDDMockito.when(studentRepository.existsByEmail(ArgumentMatchers.any(String.class)))
                .thenReturn(true);

        StudentDTO studentRequestDTO = StudentCreator.createStudentRequestDTO();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> studentService.emailBeingUsedStudent(studentRequestDTO.getEmail()));
    }
}