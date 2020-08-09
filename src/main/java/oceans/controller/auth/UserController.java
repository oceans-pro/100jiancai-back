package oceans.controller.auth;

import oceans.model.UserInfo;
import oceans.model.auth.User;
import oceans.model.dto.StatusMsgData;
import oceans.model.dto.UserDto;
import oceans.service.plain.UserInfoService;
import oceans.service.auth.UserService;
import oceans.service.plain.impl.dto.UserDtoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserDtoServiceImpl userDtoService;

    /**
     * 用户修改密码，预先ajax检查旧密码是否正确？若正确则返回1
     */
    @PostMapping("/check")
    public ResponseEntity<StatusMsgData<Boolean>> checkPass(String username, String password) {
        boolean flag = userService.checkPassword(username, password);
        return ResponseEntity.ok(
                new StatusMsgData<>(flag)
        );
    }

    /**
     * 获取当前用户的全部信息
     */
    @GetMapping("/info")
    public ResponseEntity<StatusMsgData<UserDto>> info(Authentication authentication) {
        String username = authentication.getName();
        UserDto userDto = userDtoService.getOneUserDto(username);
        return ResponseEntity.ok(new StatusMsgData<>(userDto));
    }

    /**
     * 获取所有用户的全部信息 DTO
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('authority:mod','authority:get')")
    public ResponseEntity<StatusMsgData<List<UserDto>>> getAllUsers() {
        return ResponseEntity.ok(new StatusMsgData<>(userDtoService.getAllUserDtos()));
    }

    /**
     * 封禁一名用户
     */
    @DeleteMapping("/{username}/0")
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> inactiveOneUser(@PathVariable("username") String username) {
        if ("root".equals(username)) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "无法封禁超级管理员"));
        }
        userService.inactiveOneUser(username);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "封号成功"));
    }

    /**
     * 解封一名用户
     */
    @PutMapping("/{username}/1")
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> activeOneUser(@PathVariable("username") String username) {
        if ("root".equals(username)) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "超级管理员无需解封"));
        }
        userService.activeOneUser(username);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "解封成功"));
    }

    /**
     * 删除一名用户
     */
    @DeleteMapping("/{username}")
    @Transactional
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> deleteOneUser(@PathVariable("username") String username) {
        if ("root".equals(username)) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "无法删除超级管理员"));
        }
        if (userService.selectOneByUsername(username) == null) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "无法删除不存在的用户"));
        }
        userService.deleteOneUser(username);
        userInfoService.deleteOneUser(username);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除成功"));
    }


    /**
     * 修改用户信息
     * 只有本人可以操作
     *
     * @param userInfo 用户基本信息
     */
    @PutMapping("/info")
    @PreAuthorize("principal.username.equals((#userInfo).username)")
    public ResponseEntity<StatusMsgData<String>> putInfo(String username, UserInfo userInfo) {
        if (userInfo.getId() == null) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "用户id丢失"));
        }
        userInfoService.updateUserBasicInfo(userInfo);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "修改用户信息成功"));
    }

    /**
     * 新增用户
     */
    @PostMapping("/info")
    @Transactional
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> postUserInfo(String username, String password, String email) {
        User hasRegister = userService.selectOneByUsername(username);
        if (hasRegister != null) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "该用户名已经被注册"));
        }
        userDtoService.addOneUser(username, password, email);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "注册用户成功"));
    }

    /**
     * 用户修改密码
     */
    @PutMapping("/change_pass")
    public ResponseEntity<StatusMsgData<String>> changePass(String username, String oldPass, String newPass) {
        boolean b = userService.checkPassword(username, oldPass);
        if (b) {
            userService.updateUserWithNewPassword(username, newPass);
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "密码修改成功，请重新登录！"));
        } else {
            return ResponseEntity.ok(new StatusMsgData<>("???"));
        }
    }

    /**
     * 为用户分配角色
     */
    @PutMapping("/{id}/relate")
    @PreAuthorize("hasAuthority('authority:mod')")
    public ResponseEntity<StatusMsgData<String>> putOneUserWithRoles(
            @PathVariable("id") Integer id,
            @RequestParam("roleIds") List<Integer> roleIds) {
        userService.updateOneUserWithRoleIds(id, roleIds);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "角色分配成功"));
    }
}
