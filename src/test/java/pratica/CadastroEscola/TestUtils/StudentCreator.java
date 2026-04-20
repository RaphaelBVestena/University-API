package pratica.CadastroEscola.TestUtils;

import pratica.CadastroEscola.Courses.CourseModel;
import pratica.CadastroEscola.Students.CourseSummaryDTO;
import pratica.CadastroEscola.Students.*;
import pratica.CadastroEscola.Students.StudentDTO;
import pratica.CadastroEscola.Students.StudentModel;
import pratica.CadastroEscola.Students.StudentResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentCreator {

    // MODEL (StudentModel) //

    public static StudentModel createToBeSavedStudent(){
        return StudentModel.builder()
                .id(null)
                .name("To Be Saved Student test name")
                .email("student@test.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .course(null)
                .creationTime(null)
                .updateTime(null)
                .build();
    }

    public static StudentModel createToBeSavedStudentWithCourse(CourseModel course){
        return StudentModel.builder()
                .id(null)
                .name("To Be Saved Student test name")
                .email("student@test.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .course(course)
                .creationTime(null)
                .updateTime(null)
                .build();
    }

    public static List<StudentModel> createToBeSavedStudentList(int amount){
        List<StudentModel> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(StudentModel.builder()
                    .id(null)
                    .name("To Be Saved Student test name " + i)
                    .email("student" + i + "@test.com")
                    .phone(String.valueOf(i))
                    .gender("M")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .course(null)
                    .creationTime(null)
                    .updateTime(null)
                    .build());
        }

        return list;
    }

    public static StudentModel createValidStudent(){
        return StudentModel.builder()
                .id(UUID.randomUUID())
                .name("Valid Student test name")
                .email("valid@student.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .course(null)
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static StudentModel createValidStudentWithCourse(CourseModel course){
        return StudentModel.builder()
                .id(UUID.randomUUID())
                .name("Valid Student test name")
                .email("valid@student.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .course(course)
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static List<StudentModel> createValidStudentList(int amount){
        List<StudentModel> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(StudentModel.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Student test name " + i)
                    .email("valid" + i + "@student.com")
                    .phone(String.valueOf(i))
                    .gender("M")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .course(null)
                    .creationTime(LocalDateTime.of(2020,1,1,10,0))
                    .updateTime(LocalDateTime.of(2020,1,1,10,0))
                    .build());
        }

        return list;
    }


    // COURSE SUMMARY (DTO) //

    public static CourseSummaryDTO createCourseSummaryDTO(){
        return CourseSummaryDTO.builder()
                .id(UUID.randomUUID())
                .name("Course Summary test name")
                .build();
    }


    // RESPONSE DTO //

    public static StudentResponseDTO createStudentResponseDTO(){
        return StudentResponseDTO.builder()
                .id(UUID.randomUUID())
                .name("Valid Student test name")
                .email("valid@student.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .course(null)
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static StudentResponseDTO createStudentResponseDTOWithCourse(){
        return StudentResponseDTO.builder()
                .id(UUID.randomUUID())
                .name("Valid Student test name")
                .email("valid@student.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .course(createCourseSummaryDTO())
                .creationTime(LocalDateTime.of(2020,1,1,10,0))
                .updateTime(LocalDateTime.of(2020,1,1,10,0))
                .build();
    }

    public static List<StudentResponseDTO> createStudentResponseDTOList(int amount){
        List<StudentResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(StudentResponseDTO.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Student test name " + i)
                    .email("valid" + i + "@student.com")
                    .phone(String.valueOf(i))
                    .gender("M")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .course(null)
                    .creationTime(LocalDateTime.of(2020,1,1,10,0))
                    .updateTime(LocalDateTime.of(2020,1,1,10,0))
                    .build());
        }

        return list;
    }

    public static List<StudentResponseDTO> createStudentResponseDTOListWithCourse(int amount){
        List<StudentResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < amount; i++){
            list.add(StudentResponseDTO.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Student test name " + i)
                    .email("valid" + i + "@student.com")
                    .phone(String.valueOf(i))
                    .gender("M")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .course(createCourseSummaryDTO())
                    .creationTime(LocalDateTime.of(2020,1,1,10,0))
                    .updateTime(LocalDateTime.of(2020,1,1,10,0))
                    .build());
        }

        return list;
    }


    // REQUEST DTO //

    public static StudentDTO createStudentRequestDTO(){
        return StudentDTO.builder()
                .name("Request Student test name")
                .email("request@student.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .courseId(null)
                .build();
    }

    public static StudentDTO createStudentRequestDTOWithCourse(UUID courseId){
        return StudentDTO.builder()
                .name("Request Student test name")
                .email("request@student.com")
                .phone("999999999")
                .gender("M")
                .birthDate(LocalDate.parse("2005-12-12"))
                .courseId(courseId)
                .build();
    }
}