package pratica.CadastroEscola.Security.Login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pratica.CadastroEscola.Security.Login.LoginDTO.LoginRequestDTO;
import pratica.CadastroEscola.Security.Login.LoginDTO.LoginResponseDTO;
import pratica.CadastroEscola.Security.Role.Role;
import pratica.CadastroEscola.Security.User.User;
import pratica.CadastroEscola.Security.User.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){

            var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password());

            Authentication auth = authenticationManager.authenticate(usernamePassword);

            User user = (User) auth.getPrincipal();

            Instant now = Instant.now();
            Long expiresIn = 300L;

            String scopes = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("MyBackEnd")
                    .subject(user.getId().toString())
                    .expiresAt(now.plusSeconds(expiresIn))
                    .issuedAt(now)
                    .claim("scope", scopes)
                    .build();

            String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }
}
