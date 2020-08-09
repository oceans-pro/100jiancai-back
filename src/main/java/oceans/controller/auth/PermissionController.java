package oceans.controller.auth;

import oceans.model.auth.Permission;
import oceans.model.dto.StatusMsgData;
import oceans.service.auth.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('authority:get')")
    ResponseEntity<StatusMsgData<List<Permission>>> getAllPermissions() {
        List<Permission> permissions = permissionService.selectAll();
        return ResponseEntity.ok(new StatusMsgData<>(permissions));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('authority:mod')")
    ResponseEntity<StatusMsgData<String>> deleteOnePermission(@PathVariable("id") Integer id) {
        permissionService.deleteOne(id);
        return ResponseEntity.ok(new StatusMsgData<>("删除成功"));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('authority:mod')")
    ResponseEntity<StatusMsgData<String>> postOnePermission(Permission permission) {
        Permission p = permissionService.selectOneByPermission(permission.getPermission());
        if (p != null) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "权限已经存在！不要重复添加"));
        }
        permission.setId(null);
        permissionService.insertOne(permission);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "增加权限成功"));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('authority:mod')")
    ResponseEntity<StatusMsgData<String>> putOnePermission(Permission permission) {
        String permissionName = permission.getPermission();
        Integer permissionId = permission.getId();
        if (permissionId == null|| StringUtils.isEmpty(permissionName)) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "参数错误！"));
        }
        Permission existOne = permissionService.selectOneById(permissionId);
        if (!existOne.getPermission().equals(permissionName)) {  // 如果改名了，判断是否重名
            Permission p = permissionService.selectOneByPermission(permission.getPermission());
            if (p != null) {
                return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "与其他权限名冲突！"));
            }
        }
        permissionService.updateOne(permission);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改权限成功"));
    }
}
