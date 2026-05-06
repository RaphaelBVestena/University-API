package pratica.CadastroEscola.Security.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Security.Role.Role;
import pratica.CadastroEscola.Security.Role.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public void saveNewUser(UserRequestDTO userRequest) {
        Role basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        if (userRepository.existsByUsername(userRequest.username())) {
            throw new BadRequestException("username já cadastrado");
        }

        User newUser = new User();

        newUser.setUsername(userRequest.username());
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        newUser.setRoles(Set.of(basicRole));

        userRepository.save(newUser);
    }

    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users;
    }
}
