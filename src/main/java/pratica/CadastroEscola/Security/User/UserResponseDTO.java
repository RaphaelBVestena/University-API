package pratica.CadastroEscola.Security.User;

import pratica.CadastroEscola.Security.Role.Role;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,

        String username,

        Set<String> roles
) {
}
