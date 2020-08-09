package oceans.auth;

import oceans.dao.auth.RoleDao;
import oceans.model.auth.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class RoleTest {

    @Autowired
    RoleDao roleDao;

    @Test
    @Transactional
    public void test1(){
        List<Role> all = roleDao.findAll();
        System.out.println("result => " + all);
    }
}
