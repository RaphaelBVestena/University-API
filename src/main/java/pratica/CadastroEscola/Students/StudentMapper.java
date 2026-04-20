package pratica.CadastroEscola.Students;

import pratica.CadastroEscola.Courses.CourseModel;

public class StudentMapper {

    public static void dtoToModel(StudentDTO studentDTO, StudentModel studentModel){
        studentModel.setName(studentDTO.getName());
        studentModel.setBirthDate(studentDTO.getBirthDate());
        studentModel.setEmail(studentDTO.getEmail());
        studentModel.setPhone(studentDTO.getPhone());
        studentModel.setGender(studentDTO.getGender() != null ?
                               studentDTO.getGender().trim().toUpperCase() : null );

    }

    public static StudentResponseDTO toResponseDTO(StudentModel studentModel){

        return StudentResponseDTO.builder()
                .id(studentModel.getId())
                .name(studentModel.getName())
                .birthDate(studentModel.getBirthDate())
                .email(studentModel.getEmail())
                .phone(studentModel.getPhone())
                .gender(studentModel.getGender())
                .creationTime(studentModel.getCreationTime())
                .updateTime(studentModel.getUpdateTime())
                .course(toCourseSummary(studentModel.getCourse()))
                .build();
    }

    public static void updateModel(StudentDTO studentDTO, StudentModel studentModel){
        if (studentDTO.getName() != null){
            studentModel.setName(studentDTO.getName());
        }

        if (studentDTO.getBirthDate() != null){
            studentModel.setBirthDate(studentDTO.getBirthDate());
        }

        if (studentDTO.getEmail() != null){
            studentModel.setEmail(studentDTO.getEmail());
        }

        if (studentDTO.getPhone() != null){
            studentModel.setPhone(studentDTO.getPhone());
        }

        if (studentDTO.getGender() != null){
            studentModel.setGender(studentDTO.getGender());
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
