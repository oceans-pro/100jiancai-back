package oceans.dao.auth;

import oceans.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    void deleteByUsername(String username);
}
