package oceans.dao;

import oceans.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
}
