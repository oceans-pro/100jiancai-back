package oceans.service.plain;

import oceans.model.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo selectOneByUsername(String username);

    void deleteOneUser(String username);

    List<UserInfo> selectAll();

    void insertOne(UserInfo userInfo);

    void updateUserBasicInfo(UserInfo userInfo);

}
