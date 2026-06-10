package pratica.CadastroEscola.Security.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pratica.CadastroEscola.Exceptions.BadRequestException;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;
import pratica.CadastroEscola.Security.Role.Role;
import pratica.CadastroEscola.Security.Role.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public void saveNewUser(UserRequestDTO userRequest) {

        if (userRepository.existsByUsername(userRequest.username())) {
            throw new BadRequestException("username já cadastrado");
        }

        Role basicRole = roleRepository.findByName(Role.Values.BASIC.name())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));


        User newUser = new User();

        newUser.setUsername(userRequest.username());
        newUser.setPassword(passwordEncoder.encode(userRequest.password()));
        newUser.setRoles(Set.of(basicRole));

        userRepository.save(newUser);
    }


    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())
                ))
                .toList();
    }
}