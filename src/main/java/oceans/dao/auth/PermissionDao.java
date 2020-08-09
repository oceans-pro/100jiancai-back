package oceans.dao.auth;

import oceans.model.auth.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends JpaRepository<Permission, Integer> {
    List<Permission> findAll(Sort sort);
    Permission findByPermission(String permission);
}
