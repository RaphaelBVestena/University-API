package pratica.CadastroEscola.Techers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;


    public List<TeacherModel> getAll(){
         return teacherRepository.findAll();
    }

    public TeacherResponseDTO getById(UUID id){
        TeacherModel teacherModel = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professor", "id", id));

        return TeacherMapper.toResponseDTO(teacherModel);
    }

    public TeacherResponseDTO post(TeacherDTO teacherDTO){

        //valida se o email já está cadastrado
        emailBeingUsedTeacher(teacherDTO.getEmail());

        //cria um novo professor
        TeacherModel teacherModel = new TeacherModel();

        //checa se a requisição não veio vazia
        validateTeacherDTO(teacherDTO);

        //insere os dados recebidos no novo professor
        TeacherMapper.dtoToModel(teacherDTO, teacherModel);

        //salva o novo professor
        teacherRepository.save(teacherModel);

        //retorna os dados do novo professor
        return TeacherMapper.toResponseDTO(teacherModel);
    }

    public void deleteById(UUID id){
        if (!teacherRepository.existsById(id)){
            throw new ResourceNotFoundException("professor", "id", id);
        }

        teacherRepository.deleteById(id);
    }

    public TeacherResponseDTO patchById(UUID id, TeacherDTO teacherDTO){
        TeacherModel teacherModel = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("professor", "id", id));

        //valida se o email já está cadastrado
        emailBeingUsedTeacher(teacherDTO.getEmail());

        //valida se a requisição não veio vazia
        validateTeacherDTO(teacherDTO);

        //atualiza o registro atual do professor com os dados recebidos
        TeacherMapper.updateModel(teacherModel, teacherDTO);

        //salva o registro do professor
        teacherRepository.save(teacherModel);

        return TeacherMapper.toResponseDTO(teacherModel);
    }


    //Valida se a requisição veio vazia. Se vier, lança exceção
    public static void validateTeacherDTO(TeacherDTO teacherDTO){
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
        if (teacherRepository.existsByEmail(email)){
            throw new BadRequestException("email já cadastrado");
        }
    }
}