package pratica.CadastroEscola.Students;

import ch.qos.logback.classic.boolex.StubEventEvaluator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    private final StudentService studentService;

    //lists all students
    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        return new ResponseEntity<>(studentService.getAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAllPaged(Pageable pageable){

        return new ResponseEntity<>(studentService.getAllPaged(pageable), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable UUID id){
        
        return new ResponseEntity<>(studentService.getById(id), HttpStatus.OK);
    }

    //Create a new Student record
    @PostMapping
    public ResponseEntity<StudentResponseDTO> post(@Valid @RequestBody StudentDTO studentDTO){

         return new ResponseEntity<>(studentService.post(studentDTO), HttpStatus.CREATED);
    }

    //delete a user by its id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {

        studentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //Update a property of a recorded student
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> patchById(@PathVariable UUID id, @RequestBody StudentDTO studentDTO){

        return new ResponseEntity<>(studentService.patchById(id, studentDTO), HttpStatus.OK);
    }
}