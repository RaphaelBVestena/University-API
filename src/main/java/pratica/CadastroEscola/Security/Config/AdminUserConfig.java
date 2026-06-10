package pratica.CadastroEscola.Security.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pratica.CadastroEscola.Exceptions.ResourceNotFoundException;
import pratica.CadastroEscola.Security.Role.Role;
import pratica.CadastroEscola.Security.Role.RoleRepository;
import pratica.CadastroEscola.Security.User.User;
import pratica.CadastroEscola.Security.User.UserRepository;

import java.sql.SQLOutput;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Optional<User> userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin já existe");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));

                    userRepository.save(user);
                }
        );
    }
}
