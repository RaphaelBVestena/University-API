package pratica.CadastroEscola.Security.Login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pratica.CadastroEscola.Security.Login.LoginDTO.LoginRequest;
import pratica.CadastroEscola.Security.Login.LoginDTO.LoginResponse;
import pratica.CadastroEscola.Security.User.UserRepository;

import java.time.Instant;

@RestController
public class LoginController {

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    public LoginController(BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder) ){
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now  = Instant.now();

        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("MyBackEnd")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
