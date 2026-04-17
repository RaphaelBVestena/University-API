package pratica.CadastroEscola.Techers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Log4j2
public class TeacherController {

    private final TeacherService service;

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAll() {

        return new ResponseEntity(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Page<TeacherResponseDTO>> getAllPaged(Pageable pageable){

        return new ResponseEntity<>(service.getAllPaged(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getById(@PathVariable UUID id){

        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> post(@Valid @RequestBody TeacherDTO teacherDTO){

        return new ResponseEntity<>(service.post(teacherDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id){
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<TeacherResponseDTO> patchById(@PathVariable UUID id, @RequestBody TeacherDTO dto){

        return new ResponseEntity(service.patchById(id, dto), HttpStatus.OK);
    }
}
