package pratica.CadastroEscola.TestUtils;

import pratica.CadastroEscola.Courses.CourseDTO;
import pratica.CadastroEscola.Courses.CourseModel;
import pratica.CadastroEscola.Courses.CourseResponseDTO;
import pratica.CadastroEscola.Courses.TeacherSummaryDTO;
import pratica.CadastroEscola.Teachers.TeacherModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseCreator {

    // MODEL (CourseModel) //

    public static CourseModel createToBeSavedCourse(){
        return CourseModel.builder()
                .id(null)
                .name("To Be Saved Course test name")
                .semester(8)
                .teacher(null)
                .creationTime(null)
                .updateTime(null)
                .build();
    }

    public static CourseModel createToBeSavedCourseWithTeacher(TeacherModel teacher){
        return CourseModel.builder()
                .id(null)
                .name("To Be Saved Course test name")
                .semester(8)
                .teacher(teacher)
                .creationTime(null)
                .updateTime(null)
                .build();
    }

    public static List<CourseModel> createToBeSavedCourseList(int amount){
        List<CourseModel> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(CourseModel.builder()
                    .id(null)
                    .name("To Be Saved Course test name " + i)
                    .semester(i + 1)
                    .teacher(null)
                    .creationTime(null)
                    .updateTime(null)
                    .build());
        }

        return list;
    }

    public static CourseModel createValidCourse(){
        return CourseModel.builder()
                .id(UUID.randomUUID())
                .name("Valid Course test name")
                .semester(8)
                .teacher(null)
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static CourseModel createValidCourseWithTeacher(TeacherModel teacher){
        return CourseModel.builder()
                .id(UUID.randomUUID())
                .name("Valid Course test name")
                .semester(8)
                .teacher(teacher)
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static List<CourseModel> createValidCourseList(int amount){
        List<CourseModel> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(CourseModel.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Course test name " + i)
                    .semester(i + 1)
                    .teacher(null)
                    .creationTime(LocalDateTime.of(2020,1,1,10,0))
                    .updateTime(LocalDateTime.of(2020,1,1,10,0))
                    .build());
        }

        return list;
    }


    // TEACHER SUMMARY (DTO) //

    public static TeacherSummaryDTO createTeacherSummaryDTO(){
        return TeacherSummaryDTO.builder()
                .id(UUID.randomUUID())
                .name("Teacher Summary test name")
                .build();
    }


    // RESPONSE DTO //

    public static CourseResponseDTO createCourseResponseDTO(){
        return CourseResponseDTO.builder()
                .id(UUID.randomUUID())
                .name("Valid Course test name")
                .semester(8)
                .teacher(null)
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static CourseResponseDTO createCourseResponseDTOWithTeacher(){
        return CourseResponseDTO.builder()
                .id(UUID.randomUUID())
                .name("Valid Course test name")
                .semester(8)
                .teacher(createTeacherSummaryDTO())
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static List<CourseResponseDTO> createCourseResponseDTOList(int amount){
        List<CourseResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(CourseResponseDTO.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Course test name " + i)
                    .semester(i + 1)
                    .teacher(null)
                    .creationTime(LocalDateTime.of(2020,1,1,10,0))
                    .updateTime(LocalDateTime.of(2020,1,1,10,0))
                    .build());
        }

        return list;
    }

    public static List<CourseResponseDTO> createCourseResponseDTOListWithTeacher(int amount){
        List<CourseResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(CourseResponseDTO.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Course test name " + i)
                    .semester(i + 1)
                    .teacher(createTeacherSummaryDTO())
                    .creationTime(LocalDateTime.of(2020,1,1,10,0))
                    .updateTime(LocalDateTime.of(2020,1,1,10,0))
                    .build());
        }

        return list;
    }


    // REQUEST DTO //

    public static CourseDTO createCourseRequestDTO(){
        return CourseDTO.builder()
                .name("Request Course test name")
                .semester(8)
                .teacherId(null)
                .build();
    }

    public static CourseDTO createCourseRequestDTOWithTeacher(UUID teacherId){
        return CourseDTO.builder()
                .name("Request Course test name")
                .semester(8)
                .teacherId(teacherId)
                .build();
    }
}