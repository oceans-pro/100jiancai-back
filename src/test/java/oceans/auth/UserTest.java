package oceans.auth;

import oceans.dao.auth.UserDao;
import oceans.model.auth.Permission;
import oceans.model.auth.User;
import oceans.service.auth.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class UserTest {
    @Autowired
    UserDao dao;
    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void test1() {
        List<User> all = dao.findAll();
        System.out.println("result => " + all);
        User user = dao.findById(1).orElse(null);
        System.out.println("result => " + user);
    }

    /**
     * 测试根据用户名查询所有的展平后的权限
     */
    @Test
    @Transactional
    public void test2() {
        List<Permission> permissions = userService.selectAllPermissionsByUsername("root");
        System.out.println("result => " + permissions);
    }
}
