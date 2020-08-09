package oceans.service.special.impl;

import oceans.model.dto.OnlineInfo;
import oceans.service.special.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OnlineServiceImpl implements OnlineService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 由于根据权限不同，返回的信息不同，故这里引入权限
     */
    @Value("onlinePerson:get")
    private String permissionName;

    @Override
    public OnlineInfo selectAndUpdateOnlineInfo(Authentication authentication) {
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        List<String> permissions = authorities.stream().map(item -> item.toString()).collect(Collectors.toList());
        boolean canSee = false;
        for (String permission: permissions) {
            if (permissionName.equals(permission)) {
                canSee = true;
                break;
            }
        }
        String username = principal.getUsername();
        redisTemplate.opsForValue().setIfAbsent("online:chat:" + username, "", 5, TimeUnit.SECONDS);
        Set<String> keys = redisTemplate.keys("online:chat:*");
        OnlineInfo onlineInfo = new OnlineInfo();
        Set<String> usernameList = keys.stream().map(item -> item.substring(12)).collect(Collectors.toSet());
        onlineInfo.setNum(keys.size());
        if (canSee) {
            usernameList.remove(principal.getUsername());
            onlineInfo.setUsernameList(usernameList);
        }
        return onlineInfo;
    }
}
