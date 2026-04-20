package pratica.CadastroEscola.Courses;

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
import pratica.CadastroEscola.Courses.*;
import pratica.CadastroEscola.Students.StudentRepository;
import pratica.CadastroEscola.TestUtils.CourseCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    int numberOfElements = 20;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Page<CourseModel> coursePage = new PageImpl<>(CourseCreator.createValidCourseList(numberOfElements));

        BDDMockito.when(courseRepository.findAll())
                .thenReturn(CourseCreator.createValidCourseList(numberOfElements));

        BDDMockito.when(courseRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(coursePage);

        BDDMockito.when(courseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(CourseCreator.createValidCourse()));

        BDDMockito.when(courseRepository.existsById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(true);

        BDDMockito.when(courseRepository.save(ArgumentMatchers.any(CourseModel.class)))
                .thenReturn(CourseCreator.createValidCourse());

        BDDMockito.when(courseRepository.existsByName(ArgumentMatchers.any(String.class)))
                .thenReturn(false);

        BDDMockito.doNothing().when(courseRepository).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("getAllPaged returns a List of CourseResponseDTO inside a Page when successful")
    void getAllPaged_ReturnsListOfCourseResponseDTOsInsidePage_WhenSuccessful() {

        List<CourseResponseDTO> expectedList = CourseCreator.createCourseResponseDTOList(numberOfElements);

        Page<CourseResponseDTO> serviceResponse = courseService.getAllPaged(PageRequest.of(1, 1));


        Assertions.assertThat(serviceResponse).isNotEmpty();

        Assertions.assertThat(serviceResponse.getTotalElements())
                .isEqualTo(expectedList.size());
    }

    @Test
    @DisplayName("getAll returns a List of CourseResponseDTO when successful")
    void getAll_ReturnsListOfCourseResponseDTOs_WhenSuccessful() {

        List<CourseResponseDTO> expectedList = CourseCreator.createCourseResponseDTOList(numberOfElements);

        List<CourseResponseDTO> serviceResponse = courseService.getAll();


        Assertions.assertThat(serviceResponse)
                .isNotEmpty()
                .hasSize(expectedList.size());
    }

    @Test
    @DisplayName("getById returns a CourseResponseDTO when successful")
    void getById_ReturnsCourseResponseDTO_WhenSuccessful() {

        CourseResponseDTO expectedCourse = CourseCreator.createCourseResponseDTO();

        CourseResponseDTO serviceResponse = courseService.getById(UUID.randomUUID());

        Assertions.assertThat(serviceResponse).isNotNull();

        Assertions.assertThat(serviceResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(serviceResponse.getSemester()).isEqualTo(expectedCourse.getSemester());

        Assertions.assertThat(serviceResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(serviceResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("post returns a CourseResponseDTO when successful")
    void post_ReturnsCourseResponseDTO_WhenSuccessful() {

        CourseResponseDTO expectedCourse = CourseCreator.createCourseResponseDTO();

        CourseResponseDTO serviceResponse = courseService.post(CourseCreator.createCourseRequestDTO());

        Assertions.assertThat(serviceResponse).isNotNull();

        Assertions.assertThat(serviceResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(serviceResponse.getSemester()).isEqualTo(expectedCourse.getSemester());
        
        Assertions.assertThat(serviceResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(serviceResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("delete removes a Course when successful")
    void delete_RemovesCourse_WhenSuccessful() {

        Assertions.assertThatCode(() -> courseService.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete Throws a ResourceNotFoundException when Course not found")
    void delete_ThrowsResourceNotFoundException_WhenCourseNotFound() {

        BDDMockito.when(courseRepository.existsById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(false);

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> courseService.deleteById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("patch returns a CourseResponseDTO when successful")
    void patch_ReturnsCourseResponseDTO_WhenSuccessful() {

        CourseDTO expectedCourse = CourseCreator.createCourseRequestDTO();

        CourseResponseDTO serviceResponse = courseService.patchById(UUID.randomUUID(),
                CourseCreator.createCourseRequestDTO());

        Assertions.assertThat(serviceResponse).isNotNull();

        Assertions.assertThat(serviceResponse.getName()).isEqualTo(expectedCourse.getName());
        Assertions.assertThat(serviceResponse.getSemester()).isEqualTo(expectedCourse.getSemester());

        Assertions.assertThat(serviceResponse.getId().toString()).isNotBlank();

        Assertions.assertThat(serviceResponse.getId())
                .isInstanceOf(UUID.class)
                .isNotNull();
    }

    @Test
    @DisplayName("validateCourseDTO Throws BadRequestException When CourseDTO is Empty")
    void validateCourseDTO_ThrowsBadRequestException_WhenCourseDtoIsEmpty() {
        CourseDTO nullCourseDTO = new CourseDTO();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> courseService.validateCourseDTO(nullCourseDTO))
                .withMessage("corpo da requisição não pode estar vazia");
    }

    @Test
    @DisplayName("nameBeingUsed Throws BadRequestException when Name already exists")
    void nameBeingUsed_ThrowsBadRequestException_WhenNameExists() {

        BDDMockito.when(courseRepository.existsByName(ArgumentMatchers.any(String.class)))
                .thenReturn(true);

        CourseDTO courseRequestDTO = CourseCreator.createCourseRequestDTO();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> courseService.nameBeingUsedCourse(courseRequestDTO.getName()));
    }
}