package pratica.CadastroEscola.Students;

import ch.qos.logback.classic.boolex.StubEventEvaluator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService service;

    //lists all students
    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllPaged(Pageable pageable){

        return new ResponseEntity(service.getAllPaged(pageable), HttpStatus.OK);
    }

    //Create a new Student record
    @PostMapping
    public ResponseEntity post(@Valid @RequestBody StudentDTO studentDTO){

         return new ResponseEntity(service.post(studentDTO), HttpStatus.CREATED);
    }

    //delete a user by its id
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //Update a property of a recorded student
    @PatchMapping("/{id}")
    public ResponseEntity patchById(@PathVariable UUID id, @RequestBody StudentDTO studentDTO){

        return new ResponseEntity(service.patchById(id, studentDTO), HttpStatus.OK);
    }
}