package pratica.CadastroEscola.Students;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository repository;

    @GetMapping("/getall")
    public ResponseEntity getAll(){
        List<StudentModel> studentModelList = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(studentModelList);
    }

    @PostMapping("/post")
    public ResponseEntity post(@RequestBody StudentDTO DTO){
        StudentModel Studentmodel = new StudentModel();
        BeanUtils.copyProperties(DTO, Studentmodel);

        repository.save(Studentmodel);

        return ResponseEntity.status(HttpStatus.CREATED).body(Studentmodel);
    }
}
