package pratica.CadastroEscola.Courses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@Validated
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping
    public ResponseEntity getAll() {

        return new ResponseEntity(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable UUID id){

        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody CourseDTO courseDTO){

        return new ResponseEntity(service.post(courseDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id){
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchById(@PathVariable UUID id, @RequestBody CourseDTO courseDTO){

        return new ResponseEntity(service.patchById(id, courseDTO), HttpStatus.OK);
    }
}
