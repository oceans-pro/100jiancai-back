package oceans.controller.special;

import lombok.extern.slf4j.Slf4j;
import oceans.service.api.MapService;
import oceans.model.Message;
import oceans.model.dto.StatusMsgData;
import oceans.service.plain.AppConfigService;
import oceans.service.plain.MessageService;
import oceans.service.special.MessageNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 对于留言，只有查、增、删（假）功能
 *
 * @see oceans.controller.client.PostController
 */
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
    @Resource
    private MessageService service;
    @Resource(name = "baidu")
    private MapService cityInfoService;
    @Resource
    private AppConfigService appConfigService;
    @Resource
    private MessageNoticeService messageNoticeService;

    /**
     * 查看留言（分页）
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('message:get','message:mod')")
    public ResponseEntity<StatusMsgData<Page<Message>>> getSome(Integer num, Integer size) {
        return ResponseEntity.ok(new StatusMsgData<>(service.selectSome(num - 1, size)));
    }

    /**
     * 删除一条留言
     *
     * @param id
     */
    @PreAuthorize("hasAuthority('message:mod')")
    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMsgData<List<Message>>> deleteOne(@PathVariable Integer id) {
        service.deleteOne(id);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "删除留言成功"));
    }

    /**
     * // --public
     * 接受来自客户端的消息
     * postOne(Message message, HttpServletRequest request) 无法被OperationLogAop识别方法，因为HttpServletRequest只是个接口
     */
    @PostMapping
    public ResponseEntity<StatusMsgData<String>> postOne(Message message, HttpServletRequest request) {
        message.setActive(true);
        message.setHasRead(false);
        message.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String ip;
        if (xForwardedFor == null) { // 没有使用Nginx
            ip = request.getRemoteAddr();
        } else { // 使用了Nginx
            ip = request.getHeader("X-Forwarded-For").split(",")[0];
        }
        message.setIp(ip);
        message.setCity(cityInfoService.getCityInfo(ip));
        message.setOrigin(request.getHeader("Origin"));
        message.setReferer(request.getHeader("Referer"));
        Boolean isLegal = service.checkLegal(request.getRemoteAddr());
        if (!isLegal) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "您今天已经发送了很多留言了，明天再试试~"));
        }
        service.insertOne(message);
        // async
        messageNoticeService.noticeSomeoneAsync(message);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "感谢您的留言"));
    }

    /**
     * 获取消息通知策略
     */
    @PreAuthorize("hasAuthority('message:get')")
    @GetMapping("/config")
    public ResponseEntity<StatusMsgData<Map>> getMessageConfig() {
        Map messageConfig = appConfigService.getOneByName("messageConfig");
        return ResponseEntity.ok(new StatusMsgData<>(messageConfig));
    }

    /**
     * 修改消息通知策略
     */
    @PreAuthorize("hasAuthority('message:mod')")
    @PutMapping("/config")
    public ResponseEntity<StatusMsgData<String>> putMessageConfig(@RequestBody Map map) {
        appConfigService.updateOne("messageConfig", map);
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.OK_TIP, "配置修改成功！"));
    }

}
