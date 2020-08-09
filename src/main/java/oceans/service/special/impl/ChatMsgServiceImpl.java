package oceans.service.special.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import oceans.dao.ChatMsgDao;
import oceans.model.ChatMsg;
import oceans.model.UserInfo;
import oceans.service.special.ChatMsgService;
import oceans.service.plain.UserInfoService;
import oceans.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 存放聊天室的核心业务
 * 其他业务请见
 *
 * @see ChatMsgServiceImplHelper
 */
@Service("ChatMsgService")
@Slf4j
public class ChatMsgServiceImpl implements ChatMsgService {
    @Resource
    private ChatMsgDao chatMsgDao;
    @Autowired // 只能用@Autowire的
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ChatMsgServiceImplHelper chatMsgServiceImplHelper;
    /*//////////////////////////////////////////////////////////////////////////////////////////////////
                                                 redis
    //////////////////////////////////////////////////////////////////////////////////////////////////*/

    /**
     * 获取当前消息（返回已经排好序的List）
     */
    @Override
    public List<ChatMsg> selectSomeChatMsgList(String datetime) {
        Map<String, ChatMsg> map = new HashMap<>();
        Set<String> keys = redisTemplate.keys("msg:*");
        for (String key: keys) {
            if (datetime.compareTo(key.substring(4)) < 0) { // 如果有新消息则取出，否则返回空的map
                String jsonString = redisTemplate.opsForValue().get(key);
                ChatMsg chatMsg = JsonUtil.parseJsonString(jsonString, ChatMsg.class);
                map.put(key, chatMsg);
            }
        }
        return map2List(map);
    }

    /**
     * 获取当前在线人数
     * 处理来自客户端的心跳
     */
    @Override
    public Integer selectOnlineCount(Authentication authentication) {
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        String username = principal.getUsername();
        redisTemplate.opsForValue().setIfAbsent("online:" + username, "", 5, TimeUnit.SECONDS);
        return redisTemplate.keys("online:*").size();
    }

    /**
     * 处理客户端发来的消息
     *
     * @param msg 前端发来的消息
     */
    @Override
    @Async
    public void insertOneMsg(ChatMsg msg, String username, String datetime) throws JsonProcessingException {
        // -- 封装message
        UserInfo userInfo = userInfoService.selectOneByUsername(username);
        msg.setUsername(username);
        msg.setDatetime(datetime);
        msg.setAvatar(userInfo.getAvatar());
        msg.setNickname(userInfo.getNickname());
        // -- 操作redis
        String jsonString = new ObjectMapper().writeValueAsString(msg);
        redisTemplate.opsForValue().set("msg:" + datetime, jsonString, 1, TimeUnit.DAYS);
        // -- 持久化
        chatMsgServiceImplHelper.saveChatMsgToDatabase(msg);
    }

    /*//////////////////////////////////////////////////////////////////////////////////////////////////
                                                 mysql
    //////////////////////////////////////////////////////////////////////////////////////////////////*/
    @Override
    public Page<ChatMsg> selectPage(Integer num, Integer size) {
        return chatMsgDao.findAll(PageRequest.of(
                num,
                size,
                Sort.by(Sort.Direction.DESC, "datetime")
        ));
    }

    @Override
    public void deleteAll() {
        chatMsgDao.deleteAll();
    }

    /*//////////////////////////////////////////////////////////////////////////////////////////////////
                                                    工具方法
    //////////////////////////////////////////////////////////////////////////////////////////////////*/
    @Override
    public List<ChatMsg> map2List(Map<String, ChatMsg> map) {
        List<ChatMsg> list = new ArrayList<>();
        map.forEach((key, value) -> list.add(value));
        list.sort(Comparator.comparing(ChatMsg::getDatetime));
        return list;
    }
}
