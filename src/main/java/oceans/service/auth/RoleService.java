package oceans.service.auth;

import oceans.model.auth.Role;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有角色
     */
    List<Role> selectAll();

    /**
     * 根据Id查询角色
     */
    Role selectOneById(Integer id);

    /**
     * 根据角色名查询
     */
    Role selectOneByRoleName(String roleName);

    /**
     * 根据id列表查询角色
     */
    List<Role> selectSomeByIds(List<Integer> ids);

    /**
     * 根据用户id删除角色
     */
    void deleteOneRole(Integer id);

    /**
     * 增加一个角色
     */
    void insertOneRole(Role role);

    /**
     * 更新一个角色
     */
    void updateOneRole(Role role);

    /**
     * 更新一个角色：为角色分配权限
     */
    void updateOneRoleWithPermissionsId(Integer id, List<Integer> permissionIds);

    /*+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
                                                     特殊
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-*/
    List<String> getEmailListByRoleId(Integer roleId);
}
