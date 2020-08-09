package oceans.service.special;

import oceans.model.OperationLog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OperationService {
    List<OperationLog> selectAll();

    Page<OperationLog> selectPage(Integer num, Integer size);
    void deleteAll();
    void insertOne(OperationLog operationLog);

}
