package pratica.CadastroEscola.Students;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public record StudentDTO(
        String name,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthdate,

        String email,

        int phone,

        char gender
){
}
