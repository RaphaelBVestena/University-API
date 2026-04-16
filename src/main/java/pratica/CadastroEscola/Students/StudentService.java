package pratica.CadastroEscola.Students;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratica.CadastroEscola.Courses.CourseModel;
import pratica.CadastroEscola.Courses.CourseRepository;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;


    public List<StudentResponseDTO> getAll(){

        //Busca os registros
        List<StudentModel> studentModelList = studentRepository.findAll();

        //cria uma lista de ResponseDTOs
        List<StudentResponseDTO> studentResponseDTOList = new ArrayList<>();

        //converte cada registro em um ResponseDTO
        for (StudentModel studentModel : studentModelList){
            studentResponseDTOList.add(StudentMapper.toResponseDTO(studentModel));
        }

        //retorna a lista de ResponseDTOs
        return studentResponseDTOList;
    }


    public Page<StudentResponseDTO> getAllPaged(Pageable pageable){

        Page<StudentModel> studentModels = studentRepository.findAll(pageable);

        return studentModels.map(StudentMapper::toResponseDTO);
    }


    //post a new student
    @Transactional
    public StudentResponseDTO post(StudentDTO studentDTO){

        //valida se a requisição não veio vazia
        validateStudentDTO(studentDTO);

        //valida se o Email recebido já não existe no banco
        emailBeingUsedStudent(studentDTO.getEmail());

        //cria um novo estudante
        StudentModel studentModel = new StudentModel();

        //Mapeia os campos do studentDTO para o studentModel EXCETO o Curso.Id
        StudentMapper.dtoToModel(studentDTO, studentModel);

        //se o DTO vier com Curso.Id
        if (studentDTO.getCourseId() != null) {
            //Resgata o curso referente ao ID que veio no DTO
            CourseModel courseModel = courseRepository.findById(studentDTO.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("curso", "id", studentDTO.getCourseId()));

            //insere o curso no estudante
            studentModel.setCourse(courseModel);
        }
        studentRepository.save(studentModel);

        return StudentMapper.toResponseDTO(studentModel);
    }

    //delete a student by its id
    public void deleteById(UUID id){
        if (!studentRepository.existsById(id)){
            throw new ResourceNotFoundException("Estudante", "id", id);

        }else{
            studentRepository.deleteById(id);
        }
    }

    //update name, phone or email of a recorded student by its id
    @Transactional
    public StudentResponseDTO patchById(UUID id, StudentDTO studentDTO){

        //valida se a requisição não veio vazia
        validateStudentDTO(studentDTO);

        //valida se o Email recebido já não existe no banco
        emailBeingUsedStudent(studentDTO.getEmail());

        //BUSCA o estudante referente ao ID recebido
        StudentModel studentmodel = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudante", "id", id));

        //Atualiza os campos simples. Não atualiza o Curso.
        StudentMapper.updateModel(studentDTO, studentmodel);

        //se o Curso.ID recebido no DTO não for NULL
        if (studentDTO.getCourseId() != null){

            //se o Estudante não tiver curso registrado OU se o Curso.Id recebido for diferente do registrado, atualiza
            if(studentmodel.getCourse() == null ||
               !studentDTO.getCourseId().equals(studentmodel.getCourse().getId())){

                CourseModel courseModel = courseRepository.findById(studentDTO.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("curso", "id", studentDTO.getCourseId()));

                studentmodel.setCourse(courseModel);
            }
        }

        //Salva as alterações
        studentRepository.save(studentmodel);

        //Retorna o ResponseDTO referente ao registro atualizado
        return StudentMapper.toResponseDTO(studentmodel);
    }


    //Valida se a requisição veio vazia. Se vier, lança exceção
    public static void validateStudentDTO(StudentDTO studentDTO){
        if (
                studentDTO.getName() == null &&
                        studentDTO.getBirthDate() == null &&
                        studentDTO.getEmail() == null &&
                        studentDTO.getPhone() == null &&
                        studentDTO.getGender() == null &&
                        studentDTO.getCourseId() == null
        ){
            throw new BadRequestException("corpo da requisição não pode estar vazia");
        }
    }

    //valida se o email já não existe no banco
    public void emailBeingUsedStudent(String email){
        if (studentRepository.existsByEmail(email)){
            throw new BadRequestException("email já cadastrado");
        }
    }

    //Função que Mapeia um CursoModel para um SUMÁRIO, para inseri-lo no Json do Student
    public static CourseSummaryDTO toCourseSummary(CourseModel courseModel){

        if (courseModel == null){
            return null;

        }else{
            return CourseSummaryDTO.builder()
                    .id(courseModel.getId())
                    .name(courseModel.getName())
                    .build();
        }
    }
}
