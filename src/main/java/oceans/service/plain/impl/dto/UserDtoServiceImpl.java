package oceans.service.plain.impl.dto;

import oceans.constant.AppConst;
import oceans.model.UserInfo;
import oceans.model.auth.User;
import oceans.model.dto.UserDto;
import oceans.service.plain.UserInfoService;
import oceans.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 两个实体类差的不大，可以使用 @Transient合并成一个实体类
 * 差的比较大时，使用dto
 */
@Service
public class UserDtoServiceImpl {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserService userService;

    /**
     * 整合userInfo和user为一个userDto，不涉及任何数据库操作
     */
    public static UserDto combine(User user, UserInfo userInfo) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAvatar(userInfo.getAvatar());
        userDto.setEmail(userInfo.getEmail());
        userDto.setPhone(userInfo.getPhone());
        userDto.setUsername(user.getUsername());
        userDto.setNickname(userInfo.getNickname());
        userDto.setIntroduction(userInfo.getIntroduction());
        userDto.setValid(user.getValid());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    /**
     * 获取用户信息，用于一直展示。伴随认证之后
     */
    public UserDto getOneUserDto(String username) {
        UserInfo userInfo = userInfoService.selectOneByUsername(username);
        User user = userService.selectOneByUsername(username);
        return combine(user, userInfo);
    }

    /**
     * 获取所有用户的所有信息（用户/角色/权限）
     */
    public List<UserDto> getAllUserDtos() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserInfo> userInfos = userInfoService.selectAll();
        List<User> users = userService.selectAll();
        if (userInfos.size() != users.size()) {
            throw new RuntimeException("userInfos.size()!=users.size()");
        }
        userInfos.sort(Comparator.comparingInt(UserInfo::getId));
        users.sort(Comparator.comparingInt(User::getId));
        for (int i = 0; i < userInfos.size(); i++) {
            User user = users.get(i);
            UserInfo userInfo = userInfos.get(i);
            UserDto userDto = combine(user, userInfo);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    /**
     * 增加一个用户
     *
     * @param username 用户名
     * @param password 密码
     * @param email    用户头像
     */
    public void addOneUser(String username, String password, String email) {
        // -- 两个表都插入信息
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(
                null,
                username,
                encoder.encode(password),
                true,
                null
        );
        userService.insertOne(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(username);
        userInfo.setNickname("用户: " + username);
        userInfo.setAvatar(AppConst.defaultAvatar);
        if (!StringUtils.isEmpty(email)) {
            userInfo.setEmail(email);
        }
        userInfoService.insertOne(userInfo);
    }
}
