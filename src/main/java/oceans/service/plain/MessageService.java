package oceans.service.plain;

import oceans.model.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {

    List<Message> selectAll();

    Page<Message> selectSome(Integer num, Integer size);

    void deleteOne(Integer id);

    void insertOne(Message message);

    /**
     * 检查用户是否合法
     * @param ip ip地址
     * @return 返回true代表用户合法，否则代表用户不合法
     */
    Boolean checkLegal(String ip);

}
