package oceans.controller.special;

import oceans.model.ChatMsg;
import oceans.model.OperationLog;
import oceans.model.dto.StatusMsgData;
import oceans.service.special.ChatMsgService;
import oceans.service.special.OperationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private ChatMsgService chatMsgService;
    @Resource
    private OperationService operationService;

    /*+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
                                                     chat
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-*/
    @GetMapping("/chat")
    @PreAuthorize("hasAuthority('chatLog:get')")
    public ResponseEntity<StatusMsgData<Page<ChatMsg>>> getPageChatMsgLog(Integer num, Integer pageSize) {
        Page<ChatMsg> page = chatMsgService.selectPage(num, pageSize);
        return ResponseEntity.ok(new StatusMsgData<>(page));
    }


    @DeleteMapping("/chat")
    @PreAuthorize("hasAuthority('chatLog:mod')")
    public ResponseEntity<StatusMsgData<String>> deleteAllChatLog() {
        chatMsgService.deleteAll();
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "聊天记录已经清空"));
    }
    /*+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
                                                     operation
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-*/

    @GetMapping("/operation")
    @PreAuthorize("hasAuthority('operationLog:get')")
    public ResponseEntity<StatusMsgData<Page<OperationLog>>> getPageOperationLog(Integer num, Integer pageSize) {
        Page<OperationLog> page = operationService.selectPage(num, pageSize);
        return ResponseEntity.ok(new StatusMsgData<>(page));
    }

    @DeleteMapping("/operation")
    @PreAuthorize("hasAuthority('operationLog:mod')")
    public ResponseEntity<StatusMsgData<String>> deleteAllOperationLog() {
        operationService.deleteAll();
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "操作记录已经清空"));
    }
}
