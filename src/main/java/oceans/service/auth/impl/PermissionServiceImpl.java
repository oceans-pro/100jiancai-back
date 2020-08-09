package oceans.service.auth.impl;

import oceans.dao.auth.PermissionDao;
import oceans.model.auth.Permission;
import oceans.service.auth.PermissionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<Permission> selectAll() {
        return permissionDao.findAll(Sort.by(Sort.Direction.ASC, "permission"));
    }

    @Override
    public Permission selectOneById(Integer id) {
        return permissionDao.getOne(id);
    }

    @Override
    public Permission selectOneByPermission(String permission) {
        return permissionDao.findByPermission(permission);
    }

    @Override
    public List<Permission> selectSomeByIds(List<Integer> permissionIds) {
        return permissionDao.findAllById(permissionIds);
    }

    @Override
    public void deleteOne(Integer id) {
        permissionDao.deleteById(id);
    }

    @Override
    public void insertOne(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public void updateOne(Permission permission) {
        permissionDao.save(permission);
    }

}
