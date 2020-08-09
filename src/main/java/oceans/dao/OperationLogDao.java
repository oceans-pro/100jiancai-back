package oceans.dao;

import oceans.model.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogDao extends JpaRepository<OperationLog, Integer> {
}
