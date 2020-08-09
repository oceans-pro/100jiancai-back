package oceans.service.auth.impl;

import oceans.dao.auth.UserDao;
import oceans.model.auth.Permission;
import oceans.model.auth.Role;
import oceans.model.auth.User;
import oceans.service.auth.RoleService;
import oceans.service.auth.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleService roleService;

    @Override
    public List<User> selectAll() {
        List<User> users = userDao.findAll();
        users.forEach(user -> {
            user.setPassword(null);
        });
        return users;
    }


    @Override
    public User selectOneByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean checkPassword(String username, String password) {
        String dbPassword = this.selectOneByUsername(username).getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, dbPassword);
    }


    @Override
    @Transactional // 以防 failed to lazily initialize a collection of role,could not initialize proxy - no Session
    public List<Permission> selectAllPermissionsByUsername(String username) {
        List<Permission> permissions = new ArrayList<>();
        User user = userDao.findByUsername(username);
        List<Role> roles = user.getRoles();
        roles.forEach(role -> {
            List<Permission> localList = role.getPermissions();
            permissions.addAll(localList);
        });
        return permissions;
    }

    @Override
    public void deleteOneUser(String username) {
        userDao.deleteByUsername(username);
    }

    @Override
    public void insertOne(User user) {
        userDao.save(user);
    }

    @Override
    public void activeOneUser(String username) {
        User user = userDao.findByUsername(username);
        user.setValid(true);
        userDao.save(user);
    }

    @Override
    public void inactiveOneUser(String username) {
        User user = userDao.findByUsername(username);
        user.setValid(false);
        userDao.save(user);
    }

    @Override
    public void updateUserWithNewPassword(String username, String newPassword) {
        User user = userDao.findByUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userDao.save(user);
    }

    @Override
    public void updateOneUserWithRoleIds(Integer id, List<Integer> roleIds) {
        User user = userDao.getOne(id);
        List<Role> roles = roleService.selectSomeByIds(roleIds);
        user.setRoles(roles);
        userDao.save(user);
    }
}
