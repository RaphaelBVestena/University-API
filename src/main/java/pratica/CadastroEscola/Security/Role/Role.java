package pratica.CadastroEscola.Security.Role;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Data
@Table(name = "tb_roles")
@RequiredArgsConstructor
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name;
    }

    public enum Values{
        BASIC(1L),
        ADMIN(2L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId(){
            return roleId;
        }
    }
}
