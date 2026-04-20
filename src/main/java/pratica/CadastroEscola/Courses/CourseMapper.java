package pratica.CadastroEscola.Courses;

import pratica.CadastroEscola.Teachers.TeacherModel;

public class CourseMapper {

    public static void dtoToModel(CourseDTO courseDTO, CourseModel courseModel){
        courseModel.setName(courseDTO.getName());
        courseModel.setSemester(courseDTO.getSemester());
    }

    public static CourseResponseDTO toResponseDTO(CourseModel courseModel){

        return CourseResponseDTO.builder()
                .id(courseModel.getId())
                .name(courseModel.getName())
                .semester(courseModel.getSemester())
                .creationTime(courseModel.getCreationTime())
                .updateTime(courseModel.getUpdateTime())
                .teacher(toTeacherSummary(courseModel.getTeacher()))
                .build();
    }

    public static void  updateModel(CourseModel courseModel, CourseDTO courseDTO){
        if (courseDTO.getName() != null){
            courseModel.setName(courseDTO.getName());
        }

        if (courseDTO.getSemester() != null){
            courseModel.setSemester(courseDTO.getSemester());
        }
    }

    public static TeacherSummaryDTO toTeacherSummary(TeacherModel teacherModel){
        if (teacherModel == null){
            return null;

        }else{
            return TeacherSummaryDTO.builder()
                    .id(teacherModel.getId())
                    .name(teacherModel.getName())
                    .build();
        }
    }
}
