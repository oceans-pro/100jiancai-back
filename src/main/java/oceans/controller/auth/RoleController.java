package oceans.controller.auth;

import oceans.model.auth.Role;
import oceans.model.dto.StatusMsgData;
import oceans.service.auth.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
     * 获取全部角色
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('authority:get')")
    public ResponseEntity<StatusMsgData<List<Role>>> getAll() {
        List<Role> roles = roleService.selectAll();
        return ResponseEntity.ok(new StatusMsgData<>(roles));
    }

    /**
     * 删除一个角色
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> deleteOne(@PathVariable("id") Integer id) {
        roleService.deleteOneRole(id);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除角色成功"));
    }

    /**
     * 增加一个角色
     */
    @PostMapping
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> postOne(Role role) {
        Role r = roleService.selectOneByRoleName(role.getRoleName());
        if (r != null) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "角色名字重复，请换一个"));
        }
        roleService.insertOneRole(role);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "增加一条角色成功。请分配权限~"));
    }

    /**
     * 修改一个角色
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> putOne(@PathVariable("id") Integer id, @RequestBody Role role) {
        Role existRole = roleService.selectOneById(id);
        if (!existRole.getRoleName().equals(role.getRoleName())) { // 如果角色名字发生了冲突，检查是否重名
            Role r = roleService.selectOneByRoleName(role.getRoleName());
            if (r != null) {
                return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "角色名字重复，请换一个"));
            }
        }
        roleService.updateOneRole(role);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改角色信息成功"));
    }

    /**
     * 为角色分配权限
     *
     * @param id            用户id
     * @param permissionIds 权限id数组
     */
    @PutMapping("/{id}/relate")
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> putOneWithPermissionIds(
            @PathVariable("id") Integer id,
            @RequestParam("permissionIds") List<Integer> permissionIds) {
        // 需要前端将数组转为`字符串数组` 或者 @RequestBody Map<String, Object> map（万能）
        roleService.updateOneRoleWithPermissionsId(id, permissionIds);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "权限修改成功！"));
    }

    /**
     * 获取某角色所有用户的邮箱
     *
     * @param id 角色的id
     */
    @GetMapping("/mail/{id}")
    public ResponseEntity<StatusMsgData<List<String>>> getMails(@PathVariable("id") Integer id) {
        List<String> emailListByRoleId = roleService.getEmailListByRoleId(id);
        return ResponseEntity.ok(new StatusMsgData<>(emailListByRoleId));
    }
}
