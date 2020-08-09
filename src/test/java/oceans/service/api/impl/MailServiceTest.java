package oceans.service.api.impl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;


@SpringBootTest

public class MailServiceTest {
    @Autowired
    MailService mailService;

    @Test
    public void test1() {
        mailService.sendSimpleAsync("oceans-mail@qq.com", "【测试邮件】", "这是一封测试邮件");
        mailService.sendSimpleAsync("weifang_100jiancai@163.com", "【测试邮件】", "这是一封测试邮件");
    }
}
