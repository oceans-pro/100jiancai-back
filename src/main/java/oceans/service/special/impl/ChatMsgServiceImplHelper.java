package oceans.service.special.impl;

import lombok.extern.slf4j.Slf4j;
import oceans.dao.ChatMsgDao;
import oceans.model.ChatMsg;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @see ChatMsgServiceImpl 的附属类
 */
@Service
@Slf4j
public class ChatMsgServiceImplHelper {
    @Resource
    private ChatMsgDao chatMsgDao;

    @Async
    public void saveChatMsgToDatabase(ChatMsg chatMsg) {
        chatMsg.setId(null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        chatMsgDao.save(chatMsg);
    }
}
