package pratica.CadastroDeNinjas.Students;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StudentController {

    @GetMapping("/welcome")
    public String boasVindas() {
        return  "se liga no pai kkk";
    }
}
