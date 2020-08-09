package oceans.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 既是从属的一方
 * 也是主导的一方
 */
@Data
@Entity
@Table(name = "auth_role")
@JsonIgnoreProperties({"users"})
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private List<Permission> permissions;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
