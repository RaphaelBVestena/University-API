package pratica.CadastroEscola.TestUtils;

import pratica.CadastroEscola.Teachers.TeacherDTO;
import pratica.CadastroEscola.Teachers.TeacherModel;
import pratica.CadastroEscola.Teachers.TeacherResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeacherCreator {

    // TeacherModel //

    public static TeacherModel createToBeSavedTeacher(){
        return TeacherModel.builder()
                .id(null)
                .name("To Be Saved Teacher test name")
                .email("tobesaved@teacher.com")
                .phone("999999999")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .creationTime(null)
                .updateTime(null)
                .build();
    }

    public static List<TeacherModel> createToBeSavedTeacherList(int amount){

        List<TeacherModel> list = new ArrayList<TeacherModel>();

        for (int i = 0; i < amount; i++){
            list.add(TeacherModel.builder()
                    .id(null)
                    .name("To Be Saved Teacher test name " + i)
                    .email("tobesaved" + i + "@teacher.com")
                    .phone(String.valueOf(i))
                    .gender("T")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .creationTime(null)
                    .updateTime(null)
                    .build());
        }
        return list;
    }

    public static TeacherModel createValidTeacher(){
        return TeacherModel.builder()
                .id(UUID.randomUUID())
                .name("Valid Teacher test name")
                .email("valid@teacher.com")
                .phone("999999999")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .creationTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                .updateTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                .build();
    }

    public static List<TeacherModel> createValidTeacherList(int amount){

        List<TeacherModel> list = new ArrayList<TeacherModel>();

        for (int i = 0; i < amount; i++){
            list.add(TeacherModel.builder()
                    .id(UUID.randomUUID())
                    .name("Valid Teacher test name " + i)
                    .email("valid" + i + "@teacher.com")
                    .phone(String.valueOf(i))
                    .gender("T")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .creationTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                    .updateTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                    .build());
        }
        return list;
    }


    // ResponseDTO //

    public static TeacherResponseDTO createTeacherResponseDTO(){
        return TeacherResponseDTO.builder()
                .id(UUID.randomUUID())
                .name("Valid Teacher test name")
                .email("valid@teacher.com")
                .phone("999999999")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .creationTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                .updateTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                .build();
    }

    public static List<TeacherResponseDTO> createTeacherResponseDTOList(int amount){

        List<TeacherResponseDTO> list = new ArrayList<TeacherResponseDTO>();

        for (int i = 0; i < amount; i++){
            list.add(TeacherResponseDTO.builder()
                    .id(UUID.randomUUID())
                    .name("Response Teacher test name " + i)
                    .email("response" + i + "@teacher.com")
                    .phone(String.valueOf(i))
                    .gender("T")
                    .birthDate(LocalDate.parse("2005-12-12"))
                    .creationTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                    .updateTime(LocalDateTime.of(2020, 1, 1, 10, 0))
                    .build());
        }
        return list;
    }


    // RequestDTO //

    public static TeacherDTO createTeacherRequestDTO(){
        return TeacherDTO.builder()
                .name("Request Teacher test name")
                .email("request@teacher.com")
                .phone("999999999")
                .gender("T")
                .birthDate(LocalDate.parse("2005-12-12"))
                .build();
    }
}
