package oceans.model.auth;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "auth_permission")
@JsonIgnoreProperties({"roles"})
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String permission;
    private String description;
    // 如果CascadeType.Remove：会把权限对应的角色也删除...
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private List<Role> roles;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permission='" + permission + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
