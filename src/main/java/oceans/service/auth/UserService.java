package oceans.service.auth;

import oceans.model.auth.Permission;
import oceans.model.auth.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名查询用户
     */
    User selectOneByUsername(String username);

    /**
     * 检查用户名和密码是否匹配（security已经帮我们做了）
     * 若返回值为true代表匹配成功
     */
    boolean checkPassword(String username, String password);

    /**
     * 根据用户名查询所有的权限
     */
    List<Permission> selectAllPermissionsByUsername(String username);

    /**
     * 查询所有的用户
     */
    List<User> selectAll();

    /**
     * 删除一个用户
     */
    void deleteOneUser(String username);

    /**
     * 插入一个用户
     */
    void insertOne(User user);

    /**
     * 解禁一个用户
     */
    void activeOneUser(String username);

    /**
     * 封禁一个用户
     */
    void inactiveOneUser(String username);

    /**
     * 修改用户密码
     */
    void updateUserWithNewPassword(String username, String newPassword);

    /**
     * 更新一个用户：赋予用户角色
     */
    void updateOneUserWithRoleIds(Integer id, List<Integer> roleIds);
}
