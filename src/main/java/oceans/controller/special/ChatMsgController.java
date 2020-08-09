package oceans.controller.special;

import com.fasterxml.jackson.core.JsonProcessingException;
import oceans.model.ChatMsg;
import oceans.model.dto.StatusMsgData;
import oceans.service.special.ChatMsgService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 显示今天的消息
 * 显示半天的消息
 * 显示一小时内的消息
 */
@RestController
@RequestMapping("/chat")
public class ChatMsgController {
    @Resource
    private ChatMsgService chatMsgService;

    /**
     * 获取当前消息
     */
    @GetMapping("/{datetime}")
    @PreAuthorize("hasAuthority('chat:get')")
    public ResponseEntity<StatusMsgData<List<ChatMsg>>> getMsgList(@PathVariable("datetime") String datetime) {
        return ResponseEntity.ok(new StatusMsgData<>(chatMsgService.selectSomeChatMsgList(datetime)));
    }

    /**
     * 获取当前在线人数
     * 处理来自客户端的心跳
     */
    @GetMapping("/online")
    @PreAuthorize("hasAuthority('chat:get')")
    public ResponseEntity<StatusMsgData<Integer>> getOnline(Authentication authentication) {
        return ResponseEntity.ok(new StatusMsgData<>(chatMsgService.selectOnlineCount(authentication)));
    }

    static String lastUser = "";
    static String lastDateTime = "";

    /**
     * 处理客户端发来的消息
     *
     * @param message        前端发来的消息
     * @param authentication 注意：这里如果使用的是接口，即 Authentication，则AOP无法根据方法参数名获取 Method
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('chat:mod','chat:get')")
    public ResponseEntity<StatusMsgData<String>> postOne(ChatMsg message,
                                                         UsernamePasswordAuthenticationToken authentication) throws JsonProcessingException {
        // Java时间设为二十四小时制和十二小时制的区别：
        // 1. 二十四小时制： yyyy-MM-dd HH:mm:ss
        // 2. 十二小时制：   yyyy-MM-dd hh:mm:ss
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String username = authentication.getName(); // get from memory
        if (authentication.getName().equals(lastUser) && now.equals(lastDateTime)) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "发送信息过于频繁！"));
        }
        lastUser = authentication.getName();
        lastDateTime = now;
        chatMsgService.insertOneMsg(message, username, now);
        return ResponseEntity.ok(new StatusMsgData<>("ok"));
    }
}
