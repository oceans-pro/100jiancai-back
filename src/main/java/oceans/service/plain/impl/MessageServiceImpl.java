package oceans.service.plain.impl;

import oceans.dao.MessageDao;
import oceans.dao.mapper.MessageMapper;
import oceans.model.Message;
import oceans.service.plain.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageMapper messageMapper;


    @Override
    public List<Message> selectAll() {
        return messageDao.findAll();
    }

    @Override
    public Page<Message> selectSome(Integer num, Integer size) {
        return messageDao.findAll(
                (root, query, builder) -> builder.equal(root.get("active"), true),
                PageRequest.of(num, size, Sort.by(Sort.Direction.DESC, "datetime"))
        );
    }

    @Override
    public void deleteOne(Integer id) {
        messageDao.deleteById(id);
    }

    @Override
    public void insertOne(Message message) {
        message.setId(null);
        messageDao.save(message);
    }

    /**
     * 同一IP当日发送的消息数目是否大于10
     */
    @Override
    public Boolean checkLegal(String ip) {
        String datetimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String dateStr = datetimeStr.substring(0, 10);
        Integer num = messageMapper.selectExistNumByIpAndByDateStr(ip, dateStr);
        return num < 10;
    }
}
