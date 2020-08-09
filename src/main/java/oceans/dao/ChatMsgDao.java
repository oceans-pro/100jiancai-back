package oceans.dao;

import oceans.model.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMsgDao extends JpaRepository<ChatMsg, Integer> {
}
