package oceans.service.special;

import oceans.model.Message;
import org.springframework.scheduling.annotation.Async;

public interface MessageNoticeService {
    /**
     * 向一些人发送邮件
     */
    @Async
    void noticeSomeoneAsync(Message message);
}
