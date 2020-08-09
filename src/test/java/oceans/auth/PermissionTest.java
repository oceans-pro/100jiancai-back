package oceans.auth;

import oceans.dao.auth.PermissionDao;
import oceans.model.auth.Permission;
import oceans.model.auth.Role;
import oceans.model.auth.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PermissionTest {

    @Autowired
    PermissionDao permissionDao;

    @Test
    @Transactional
    public void test1() {
        List<Permission> all = permissionDao.findAll();
        System.out.println("result => " + all);
    }

    /**
     * findOne()是返回的是一个实体对象，查不到的时候会返回null
     * getOne()是返回的一个对象的引用，查不到会抛异常
     */
    @Test
    @Transactional
    public void tes2() {
        int id = 1;
        Permission permission0 = new Permission();
        permission0.setId(id);

        Optional<Permission> optional1 = permissionDao.findById(1);

        optional1.ifPresent(permission1 -> System.out.println("result => " + permission1));
        Permission permission2 = optional1.orElse(null);
        if (optional1.isPresent()) {
            Permission permission3 = optional1.get();
        }
        Optional<Permission> optional2 = permissionDao.findOne(Example.of(permission0));
        optional2.ifPresent(p -> System.out.println("result => " + p));

        System.out.println("result => " + permissionDao.getOne(id));
    }

    @Test
    @Transactional
    public void tes3() {
        List<Role> roles = permissionDao.findById(1).get().getRoles();
        System.out.println("result => " + roles);
        List<User> users = permissionDao.findById(1).get().getRoles().get(0).getUsers();
        System.out.println("result => " + users);
    }
}
