package oceans.service.auth;
import oceans.model.auth.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 查询所有权限
     */
    List<Permission> selectAll();
    /**
     * 根据id查询权限
     */
    Permission selectOneById(Integer id);
    /**
     * 根据permission查询权限
     */
    Permission selectOneByPermission(String permission);
    /**
     * 根据id列表查询权限
     */
    List<Permission> selectSomeByIds(List<Integer> permissionIds);
    /**
     * 根据Id删除权限
     */
    void deleteOne(Integer id);
    /**
     * 插入一条权限
     */
    void insertOne(Permission permission);

    /**
     * 更新一条权限
     */
    void updateOne(Permission permission);

}
