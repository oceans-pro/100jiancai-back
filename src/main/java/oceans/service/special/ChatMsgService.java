package oceans.service.special;

import com.fasterxml.jackson.core.JsonProcessingException;
import oceans.model.ChatMsg;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface ChatMsgService {
    List<ChatMsg> selectSomeChatMsgList(String datetime);

    Integer selectOnlineCount(Authentication authentication);

    @Async
    void insertOneMsg(ChatMsg msg, String username, String datetime) throws JsonProcessingException;

    Page<ChatMsg> selectPage(Integer num, Integer size);

    void deleteAll();

    List<ChatMsg> map2List(Map<String, ChatMsg> map);
}
