package pratica.CadastroEscola.Courses;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;
import pratica.CadastroEscola.Students.StudentRepository;
import pratica.CadastroEscola.Teachers.TeacherModel;
import pratica.CadastroEscola.Teachers.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;


    public List<CourseResponseDTO> getAll(){

        //Busca os registros
        List<CourseModel> courseModelList = courseRepository.findAll();

        //cria uma lista de ResponseDTOs
        List<CourseResponseDTO> courseResponseDTOList = new ArrayList<>();

        //converte cada registro em um ResponseDTO
        for (CourseModel courseModel : courseModelList){
            courseResponseDTOList.add(CourseMapper.toResponseDTO(courseModel));
        }

        //retorna a lista de ResponseDTOs
        return courseResponseDTOList;
    }


    public Page<CourseResponseDTO> getAllPaged(Pageable pageable){

        Page<CourseModel> courseModels = courseRepository.findAll(pageable);

        return courseModels.map(CourseMapper::toResponseDTO);
    }

    public CourseResponseDTO getById(UUID courseId){

        //busca o curso referente ao ID recebido
        CourseModel courseModel = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("curso", "id", courseId));

        //retorna os dados filtrados do curso buscado
        return CourseMapper.toResponseDTO(courseModel);
    }


    //POST
    @Transactional
    public CourseResponseDTO post(CourseDTO courseDTO){

        //cria um novo curso
        CourseModel courseModel = new CourseModel();

        //valida se a requisição não veio vazia
        validateCourseDTO(courseDTO);

        //valida se o nome Já não existe no banco
        nameBeingUsedCourse(courseDTO.getName());

        //Mapeia os campos EXCETO o teacherId
        CourseMapper.dtoToModel(courseDTO, courseModel);

        //Busca o Teacher pelo Id e insere o referente TeacherModel CourseModel criado
        if (courseDTO.getTeacherId() != null){
            TeacherModel teacherModel = teacherRepository.findById(courseDTO.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("professor", "id", courseDTO.getTeacherId()));

            courseModel.setTeacher(teacherModel);
        }

        return CourseMapper.toResponseDTO(
                courseRepository.save(courseModel));
    }


    //DELETE a Course
    public void deleteById(UUID id){
        if(!courseRepository.existsById(id)){
            throw new ResourceNotFoundException("curso", "id", id);
        }

        if (studentRepository.existsByCourseId(id)){
            throw new BadRequestException("Curso possui alunos vinculados");
        }
        courseRepository.deleteById(id);
    }


    //PATCH
    @Transactional
    public CourseResponseDTO patchById(UUID id, CourseDTO courseDTO){

        //valida se a requisição não veio vazia
        validateCourseDTO(courseDTO);

        //valida se o nome Já não existe no banco
        nameBeingUsedCourse(courseDTO.getName());

        //busca o curso referente ao ID recebido
        CourseModel courseModel = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id));

        //atualiza os campos simples. Não atualiza TeacherId
        CourseMapper.updateModel(courseModel, courseDTO);

        //Se o TeacherId recebido não for NULL e for diferente do atual, atualize
        if (courseDTO.getTeacherId() != null) {

            if (courseModel.getTeacher() == null ||
                !courseDTO.getTeacherId().equals(courseModel.getTeacher().getId())) {

                //Busca o TeacherModel referente ao ID recebido no CourseDTO
                TeacherModel teacherModel = teacherRepository.findById(courseDTO.getTeacherId())
                        .orElseThrow(() -> new ResourceNotFoundException("professor", "id", courseDTO.getTeacherId()));

                //Atualiza o Teacher atual
                courseModel.setTeacher(teacherModel);
            }
        }

        courseRepository.save(courseModel);

        return CourseMapper.toResponseDTO(courseModel);
    }


    public void validateCourseDTO(CourseDTO courseDTO){
        if (
                courseDTO.getName() == null &&
                        courseDTO.getSemester() == null &&
                        courseDTO.getTeacherId() == null

        ){
            throw new BadRequestException("corpo da requisição não pode estar vazia");
        }
    }

    //valida se já existe um curso com o nome recebido. Evita duplicidade
    public void nameBeingUsedCourse(String name){
        if (courseRepository.existsByName(name)){
            throw new BadRequestException("nome já cadastrado");
        }
    }
}
