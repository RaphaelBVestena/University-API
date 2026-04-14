package pratica.CadastroEscola.Techers;

import pratica.CadastroEscola.Exceptions.BadRequestException;

public class TeacherMapper {

    // TeacherDTO -> new TeacherModel (POST)
    public static void dtoToModel(TeacherDTO teacherDTO, TeacherModel teacherModel) {
        teacherModel.setName(teacherDTO.getName());
        teacherModel.setBirthDate(teacherDTO.getBirthDate());
        teacherModel.setEmail(teacherDTO.getEmail());
        teacherModel.setPhone(teacherDTO.getPhone());
        teacherModel.setGender(           // deixa o gender em caixa alta. ( "m" -> "M" )
                teacherDTO.getGender() != null ? teacherDTO.getGender().trim().toUpperCase() : null);
    }

    //TeacherModel -> TeacherResponseDTO -- Para retorno em ResponseEntity e etc...
    public static TeacherResponseDTO toResponseDTO( TeacherModel teacherModel){
        return TeacherResponseDTO.builder()
                .id(teacherModel.getId())
                .name(teacherModel.getName())
                .birthDate(teacherModel.getBirthDate())
                .email(teacherModel.getEmail())
                .gender(teacherModel.getGender())
                .creationTime(teacherModel.getCreationTime())
                .updateTime(teacherModel.getUpdateTime())
                .build();
    }

    //Inserir dados De TeacherDTO -> TeacherModel JÁ EXISTENTE - somente campos NOT NULL serão considerados
    public static void updateModel(TeacherModel teacherModel, TeacherDTO teacherDTO){
        if (teacherDTO.getName() != null){
            teacherModel.setName(teacherDTO.getName());
        }
        if (teacherDTO.getBirthDate() != null){
            teacherModel.setBirthDate(teacherDTO.getBirthDate());
        }
        if (teacherDTO.getEmail() != null){
            teacherModel.setEmail(teacherDTO.getEmail());
        }
        if (teacherDTO.getPhone() != null){
            teacherModel.setPhone(teacherDTO.getPhone());
        }
        if (teacherDTO.getGender() != null){
            teacherModel.setGender(teacherDTO.getGender().trim().toUpperCase());
        }
    }
}
