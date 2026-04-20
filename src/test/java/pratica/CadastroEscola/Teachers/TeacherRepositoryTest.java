package pratica.CadastroEscola.Teachers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pratica.CadastroEscola.TestUtils.TeacherCreator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    
    // SAVE AND UPDATE
    
    @Test
    @DisplayName("Save creates a new teacher when successful")
    public void save_PersistTeacher_WhenSuccessful(){

        TeacherModel teacherToBeSaved = TeacherCreator.createToBeSavedTeacher();
        TeacherModel savedTeacher = this.teacherRepository.saveAndFlush(teacherToBeSaved);

        Assertions.assertThat(savedTeacher).isNotNull();
        Assertions.assertThat(savedTeacher.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates the teacher when successful")
    public void save_UpdateTeacher_WhenSuccessful(){

        TeacherModel teacherToBeSaved = TeacherCreator.createToBeSavedTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(teacherToBeSaved);

        savedTeacher.setName("Updated Teacher test name");
        savedTeacher.setEmail("Updated Teacher test email");
        savedTeacher.setPhone("Updated Teacher test phone");
        savedTeacher.setGender("u");

        TeacherModel reloadedTeacher = this.teacherRepository.findById(savedTeacher.getId()).get();

        this.teacherRepository.flush();

        Assertions.assertThat(reloadedTeacher).isNotNull();
        Assertions.assertThat(reloadedTeacher.getId()).isNotNull();

        Assertions.assertThat(reloadedTeacher.getId()).isEqualTo(savedTeacher.getId());
        Assertions.assertThat(reloadedTeacher.getName()).isEqualTo("Updated Teacher test name");
        Assertions.assertThat(reloadedTeacher.getEmail()).isEqualTo("Updated Teacher test email");
        Assertions.assertThat(reloadedTeacher.getPhone()).isEqualTo("Updated Teacher test phone");
        Assertions.assertThat(reloadedTeacher.getGender()).isEqualTo("u");
    }

    @Test
    @DisplayName("SaveAll saves a teacher list when successful")
    public void saveAll_PersistTeacherList_WhenSuccessful(){
        int amount = 10;

        List<TeacherModel> toBeSavedTeacherList = TeacherCreator.createToBeSavedTeacherList(amount);

        List<TeacherModel> savedTeacherList = this.teacherRepository.saveAll(toBeSavedTeacherList);

        this.teacherRepository.flush();

        Assertions.assertThat(savedTeacherList)
                .isNotEmpty()
                .hasSize(amount);
    }
    
    // DELETE
    
    @Test
    @DisplayName("Delete removes teacher when successful")
    public void delete_RemovesTeacher_WhenSuccessful(){

        TeacherModel teacherToBeSaved = TeacherCreator.createToBeSavedTeacher();
        TeacherModel savedTeacher = this.teacherRepository.save(teacherToBeSaved);

        this.teacherRepository.deleteById(savedTeacher.getId());

        Optional<TeacherModel> teacherOptional = this.teacherRepository.findById(savedTeacher.getId());

        Assertions.assertThat(teacherOptional).isEmpty();
    }

    
    //EXISTS BY EMAIL
    
    @Test
    @DisplayName("Exists By Email returns True when successful" )
    public void existsByEmail_ReturnsTrue_WhenSuccessful(){

        TeacherModel teacherToBeSaved = TeacherCreator.createToBeSavedTeacher();
        TeacherModel savedTeacher = this.teacherRepository.saveAndFlush(teacherToBeSaved);

        boolean existsByEmail = this.teacherRepository.existsByEmail(savedTeacher.getEmail());

        Assertions.assertThat(existsByEmail).isTrue();
        Assertions.assertThat(savedTeacher.getEmail()).isNotEmpty();
    }

    @Test
    @DisplayName("Exists By Email returns False when successful" )
    public void existsByEmail_ReturnsFalse_WhenSuccessful(){

        TeacherModel teacherToBeSaved = TeacherCreator.createToBeSavedTeacher();
        TeacherModel savedTeacher = this.teacherRepository.saveAndFlush(teacherToBeSaved);

        boolean existsByEmail = this.teacherRepository.existsByEmail("non-existent email");

        Assertions.assertThat(existsByEmail).isFalse();
        Assertions.assertThat(savedTeacher.getEmail()).isNotEmpty();
    }

    
    // FIND BY ID
    
    @Test
    @DisplayName("FindById returns course when found")
    void findById_ReturnsTeacher_WhenSuccessful() {
        TeacherModel savedTeacher = this.teacherRepository.save(TeacherCreator.createToBeSavedTeacher());

        Optional<TeacherModel> result = this.teacherRepository.findById(savedTeacher.getId());

        Assertions.assertThat(result).isPresent();
    }

    @Test
    @DisplayName("FindById returns empty when not found")
    void findById_ReturnsEmpty_WhenNotFound() {
        Optional<TeacherModel> result = this.teacherRepository.findById(UUID.randomUUID());

        Assertions.assertThat(result).isEmpty();
    }

    
    // FIND ALL

    @Test
    @DisplayName("FindAll returns a Teachers List when successful" )
    public void findAll_ReturnsTeachersList_WhenSuccessful(){
        int amount = 10;

        List<TeacherModel> teachersList = TeacherCreator.createToBeSavedTeacherList(amount);

        this.teacherRepository.saveAll(teachersList);

        List<TeacherModel> resultList = this.teacherRepository.findAll();

        Assertions.assertThat(resultList).isNotEmpty();
        Assertions.assertThat(resultList.size()).isEqualTo(amount);
    }

    @Test
    @DisplayName("FindAll returns empty List when successful" )
    public void findAll_ReturnsEmptyList_WhenSuccessful(){

        List<TeacherModel> resultList = this.teacherRepository.findAll();

        Assertions.assertThat(resultList).isEmpty();
        Assertions.assertThat(resultList).isNotNull();
    }
    
    @Test
    @DisplayName("FindAll returns 2 pages with content when successful" )
    public void findAll_returns2PagesWithContent_WhenSuccessful(){
        int amount = 10;

        this.teacherRepository.saveAll(TeacherCreator.createToBeSavedTeacherList(amount));

        Page<TeacherModel> teachersList1 = this.teacherRepository.findAll(PageRequest.of(0, amount/2));
        Page<TeacherModel> teachersList2 = this.teacherRepository.findAll(PageRequest.of(1, amount/2));

        Assertions.assertThat(teachersList1).isNotEmpty();
        Assertions.assertThat(teachersList2).isNotEmpty();

        Assertions.assertThat(teachersList1.getNumberOfElements()).isEqualTo(amount/2);
        Assertions.assertThat(teachersList2.getNumberOfElements()).isEqualTo(amount/2);

        Assertions.assertThat(teachersList1.getNumber()).isEqualTo(0);
        Assertions.assertThat(teachersList2.getNumber()).isEqualTo(1);

        Assertions.assertThat(teachersList1.isFirst()).isTrue();
        Assertions.assertThat(teachersList2.isLast()).isTrue();
    }

    @Test
    @DisplayName("FindAll returns a empty page when successful" )
    public void findAll_ReturnsEmptyPage_WhenSuccessful(){

        Page<TeacherModel> teachersList = this.teacherRepository.findAll(PageRequest.of(0, 20));

        Assertions.assertThat(teachersList).isNotNull();
        Assertions.assertThat(teachersList.getContent()).isEmpty();
    }
}