package oceans.dao.auth;

import oceans.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Role findByRoleName(String roleName);
}
