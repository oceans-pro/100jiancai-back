package oceans.service.auth.impl;

import oceans.dao.auth.RoleDao;
import oceans.dao.mapper.RoleMapper;
import oceans.model.auth.Permission;
import oceans.model.auth.Role;
import oceans.service.auth.PermissionService;
import oceans.service.auth.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionService permissionService;

    @Override
    public List<Role> selectAll() {
        return roleDao.findAll();
    }

    @Override
    public Role selectOneById(Integer id) {
        return roleDao.getOne(id);
    }

    @Override
    public Role selectOneByRoleName(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

    @Override
    public List<Role> selectSomeByIds(List<Integer> ids) {
        return roleDao.findAllById(ids);
    }

    @Override
    public void deleteOneRole(Integer id) {
        roleDao.deleteById(id);
    }

    @Override
    public void insertOneRole(Role role) {
        role.setId(null);
        roleDao.save(role);
    }

    @Override
    public void updateOneRole(Role role) {
        roleDao.save(role);
    }

    @Override
    public void updateOneRoleWithPermissionsId(Integer id, List<Integer> permissionIds) {
        List<Permission> permissions = permissionService.selectSomeByIds(permissionIds);
        Role role = roleDao.getOne(id);
        role.setPermissions(permissions);
        roleDao.save(role);
    }

    @Override
    public List<String> getEmailListByRoleId(Integer roleId) {
        return roleMapper.getEmailListByRoleId(roleId);
    }
}
