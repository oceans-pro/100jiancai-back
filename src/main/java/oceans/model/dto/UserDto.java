package oceans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oceans.model.auth.Role;

import java.util.List;

/**
 * UserInfo + User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String nickname;
    private String phone;
    private String email;
    private String avatar;
    private String introduction;
    private Boolean valid;
    private List<Role> roles;
}
