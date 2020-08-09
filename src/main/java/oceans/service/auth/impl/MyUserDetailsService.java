package oceans.service.auth.impl;

import javafx.fxml.LoadException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import oceans.dao.auth.UserDao;
import oceans.dao.auth.RoleDao;
import oceans.listener.exception.MyLockedUserException;
import oceans.listener.exception.MyUsernameNotFoundException;
import oceans.model.auth.Permission;
import oceans.model.auth.User;
import oceans.service.auth.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 替换SpringSecurity自带的UserDetailsService实现类
 * 只能使用基于权限的
 * 不能使用基于角色的
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new MyUsernameNotFoundException("用户不存在！");
        }
        if (user != null && !user.getValid()) {
            throw new MyLockedUserException("您的账号暂时被封禁！");
        }
        // -- 使用更加通用的五表认证而不是三表认证
        // List<Role> roles = roleDao.findByUsername(username);
        // User user = userService.selectOneByUsername(username);
        // List<Role> roles = user.getRoles();
        List<Permission> permissions = userService.selectAllPermissionsByUsername(username);
        // 对用户、权限进行封装
        List<SimpleGrantedAuthority> authorityList = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorityList
        );
    }
}
