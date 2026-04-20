package pratica.CadastroEscola.Courses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @GetMapping()
    public ResponseEntity<Page<CourseResponseDTO>> getAllPaged(Pageable pageable){

        return new ResponseEntity<>(service.getAllPaged(pageable), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseResponseDTO>> getAll() {

        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getById(@PathVariable UUID id){

        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> post(@Valid @RequestBody CourseDTO courseDTO){

        return new ResponseEntity<>(service.post(courseDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> patchById(@PathVariable UUID id, @RequestBody CourseDTO courseDTO){

        return new ResponseEntity<>(service.patchById(id, courseDTO), HttpStatus.OK);
    }
}
