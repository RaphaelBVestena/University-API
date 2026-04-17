package pratica.CadastroEscola.TestUtils;

import pratica.CadastroEscola.Techers.TeacherDTO;
import pratica.CadastroEscola.Techers.TeacherMapper;
import pratica.CadastroEscola.Techers.TeacherModel;
import pratica.CadastroEscola.Techers.TeacherResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeacherCreator {

    public static TeacherModel createNewTeacher(){
        TeacherModel teacherModel = new TeacherModel();

        TeacherMapper.dtoToModel(createTeacherRequestDTO(), teacherModel);

        return teacherModel;
    }


    public static TeacherModel createValidTeacher(){
        return TeacherModel.builder()
                .id(UUID.randomUUID())
                .name("Valid Teacher test Name")
                .email("Valid Teacher test Email")
                .phone("Valid teacher test Phone")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .creationTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }


    public static List<TeacherModel> createNewTeachersList(int amount){
        List<TeacherModel> teacherModels = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            teacherModels.add(TeacherModel.builder()
                    .id(null)
                    .name("new Teacher" + String.valueOf(i))
                    .email("New Teacher Test Email " + String.valueOf(i))
                    .phone(String.valueOf(i))
                    .gender("T")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .creationTime(null)
                    .updateTime(null)
                    .build());
        }

        return teacherModels;
    }


    public static List<TeacherModel> createValidTeachersList(int amount){

        List<TeacherModel> teacherModels = new ArrayList<TeacherModel>();

        for (int i = 0; i < amount; i++){
            teacherModels.add(TeacherModel.builder()
                    .id(UUID.randomUUID())
                    .name("valid Teacher " + String.valueOf(i))
                    .email("Valid Teacher Test Email " + String.valueOf(i))
                    .phone(String.valueOf(i))
                    .gender("T")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .creationTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build());
        }
        return teacherModels;
    }


    public static TeacherResponseDTO createTeacherResponseDTO(){
        return TeacherMapper.toResponseDTO(createValidTeacher());
    }


    public static List<TeacherResponseDTO> createTeacherResponseDTOList(int amount){
        return createNewTeachersList(amount).
                stream().map(TeacherMapper::toResponseDTO).toList();
    }

    public static TeacherDTO createTeacherRequestDTO(){
        return TeacherDTO.builder()
                .name("New Teacher test Name")
                .email("New Teacher test Email")
                .phone("New teacher test Phone")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .build();
    }
}
