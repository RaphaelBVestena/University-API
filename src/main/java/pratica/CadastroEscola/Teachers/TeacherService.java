package pratica.CadastroEscola.Teachers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pratica.CadastroEscola.Courses.CourseRepository;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final CourseRepository courseRepository;


    public List<TeacherResponseDTO> getAll(){

        //monta uma lista com todos os professores do banco
         List<TeacherModel> teacherModels = teacherRepository.findAll();

         //cria uma Lista de objetos de resposta
         List<TeacherResponseDTO> teacherResponseDTOs = new ArrayList<>();

         //adiciona os professor do banco na lista de objetos de resposta
         for (TeacherModel teacherModel : teacherModels){
             teacherResponseDTOs.add(TeacherMapper.toResponseDTO(teacherModel));
         }

         //retorna a lista de objetos de resposta
         return teacherResponseDTOs;
    }

    public Page<TeacherResponseDTO> getAllPaged(Pageable pageable){

        Page<TeacherModel> teacherModels = teacherRepository.findAll(pageable);

        return teacherModels.map(TeacherMapper::toResponseDTO);
    }

    public TeacherResponseDTO getById(UUID id){
        TeacherModel teacherModel = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professor", "id", id));

        return TeacherMapper.toResponseDTO(teacherModel);
    }

    @Transactional
    public TeacherResponseDTO post(TeacherDTO teacherDTO){

        //valida se o email já está cadastrado
        emailBeingUsedTeacher(teacherDTO.getEmail());

        //cria um novo professor
        TeacherModel teacherModel = new TeacherModel();

        //checa se a requisição não veio vazia
        validateTeacherDTO(teacherDTO);

        //insere os dados recebidos no novo professor
        TeacherMapper.dtoToModel(teacherDTO, teacherModel);

        //salva os dados e retorna os dados do novo professor
        return TeacherMapper.toResponseDTO(teacherRepository.save(teacherModel));
    }

    public void deleteById(UUID id){
        if (!teacherRepository.existsById(id)){
            throw new ResourceNotFoundException("professor", "id", id);
        }

        if (courseRepository.existsByTeacherId(id)){
            throw new BadRequestException("Professor possui curso cadastrado");
        }

        teacherRepository.deleteById(id);
    }

    @Transactional
    public TeacherResponseDTO patchById(UUID id, TeacherDTO teacherDTO){
        TeacherModel teacherModel = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professor", "id", id));

        //valida se o email já está cadastrado
        emailBeingUsedTeacher(teacherDTO.getEmail());

        //valida se a requisição não veio vazia
        validateTeacherDTO(teacherDTO);

        //atualiza o registro atual do professor com os dados recebidos
        TeacherMapper.updateModel(teacherModel, teacherDTO);

        //salva o registro do professor e retorna um objeto de resposta
        return TeacherMapper.toResponseDTO(teacherRepository.save(teacherModel));
    }


    //Valida se a requisição veio vazia. Se vier, lança exceção
    public void validateTeacherDTO(TeacherDTO teacherDTO){
        if (
            teacherDTO.getName() == null &&
            teacherDTO.getBirthDate() == null &&
            teacherDTO.getEmail() == null &&
            teacherDTO.getPhone() == null &&
            teacherDTO.getGender() == null)
        {
            throw new BadRequestException("corpo da requisição não pode estar vazia");
        }
    }

    //valida se já existe um professor com o Email recebido. Evita duplicidade
    public void emailBeingUsedTeacher(String email){

        if (email != null) {

            if (teacherRepository.existsByEmail(email)) {
                throw new BadRequestException("email já cadastrado");
            }
        }
    }
}