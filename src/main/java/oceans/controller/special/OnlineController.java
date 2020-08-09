package oceans.controller.special;

import oceans.model.dto.OnlineInfo;
import oceans.model.dto.StatusMsgData;
import oceans.service.special.OnlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 获取当前在线人数
 * 处理来自客户端的心跳
 */
@RestController
@RequestMapping("/online")
public class OnlineController {
    @Resource
    private OnlineService onlineService;

    @GetMapping("/chat")
    public ResponseEntity<StatusMsgData<OnlineInfo>> getAndPutOnlineInfo(Authentication authentication) {
        return ResponseEntity.ok(new StatusMsgData<>(onlineService.selectAndUpdateOnlineInfo(authentication)));
    }
}
