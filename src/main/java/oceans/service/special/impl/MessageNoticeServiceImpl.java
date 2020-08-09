package oceans.service.special.impl;

import lombok.extern.slf4j.Slf4j;
import oceans.model.Message;
import oceans.service.api.impl.MailService;
import oceans.service.auth.RoleService;
import oceans.service.plain.AppConfigService;
import oceans.service.special.MessageNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageNoticeServiceImpl implements MessageNoticeService {
    @Resource
    private RoleService roleService;
    @Resource
    private AppConfigService appConfigService;
    @Resource
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 根据通知配置进行发送邮件/不发送邮件
     */
    @Override
    @Async
    public void noticeSomeoneAsync(Message message) {
        Map messageConfig = appConfigService.getOneByName("messageConfig");
        int noticeType = (int)messageConfig.get("noticeType");
        List<String> emails = new ArrayList<>();
        if (noticeType == 1) {
            int roleId = (int)messageConfig.get("roleId");
            emails = roleService.getEmailListByRoleId(roleId);
        }
        // -- 简单发送
        // String subject = message.getTitle();
        // String text = message.getContent();
        // for (String email: emails) {
        //     mailService.sendSimpleAsync(email, subject, text);
        // }
        // -- 富文本发送
        // 获取内容
        String subject = message.getTitle();
        String content = message.getContent();
        String ip = message.getIp();
        String city = message.getCity();
        String datetime = message.getDatetime();
        // 读取html
        Context context = new Context();
        context.setVariable("title", subject);
        context.setVariable("content", content);
        context.setVariable("ip", ip);
        context.setVariable("city", city);
        context.setVariable("datetime", datetime);
        String htmlStr = templateEngine.process("email", context);
        for (String email: emails) {
            log.info("发送邮件中...");
            try {
                mailService.sendRichAsync(email, subject, htmlStr);
            } catch (MessagingException e) {
                log.error("邮件发送失败", e);
            }
        }
    }
}
