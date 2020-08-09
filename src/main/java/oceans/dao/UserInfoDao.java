package oceans.dao;

import oceans.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Integer>  {
    UserInfo findByUsername(String username);
    void deleteByUsername(String username);
}
