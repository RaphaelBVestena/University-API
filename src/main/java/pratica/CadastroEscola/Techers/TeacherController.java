package pratica.CadastroEscola.Techers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratica.CadastroEscola.Courses.CourseDTO;
import pratica.CadastroEscola.Courses.CourseModel;
import pratica.CadastroEscola.Courses.CourseRepository;
import pratica.CadastroEscola.Courses.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService service;
    private final TeacherRepository repository;

    @GetMapping
    public ResponseEntity getAll() {

        return new ResponseEntity(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable UUID id){

        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody TeacherDTO teacherDTO){

        return new ResponseEntity(service.post(teacherDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id){
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity patchById(@PathVariable UUID id, @RequestBody TeacherDTO dto){

        return new ResponseEntity(service.patchById(id, dto), HttpStatus.OK);
    }
}
