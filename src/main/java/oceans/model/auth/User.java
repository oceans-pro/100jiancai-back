package oceans.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 存储关于用户的认证信息
 * toString单向查询
 * json单向查询
 */
@Data
@Entity
@Table(name = "auth_user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private Boolean valid;
    @ManyToMany(fetch = FetchType.LAZY) // @JoinTable描述了中间表
    @JoinTable(name = "auth_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;
}
