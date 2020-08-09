package oceans.service.plain.impl;

import oceans.dao.UserInfoDao;
import oceans.model.UserInfo;
import oceans.service.plain.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public List<UserInfo> selectAll() {
        return userInfoDao.findAll();
    }
    @Override
    public UserInfo selectOneByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }
    @Override
    public void deleteOneUser(String username) {
        userInfoDao.deleteByUsername(username);
    }

    @Override
    public void insertOne(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }
    @Override
    public void updateUserBasicInfo(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }

}
