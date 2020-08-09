package oceans.service.special.impl;

import oceans.dao.OperationLogDao;
import oceans.model.OperationLog;
import oceans.service.special.OperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    @Resource
    private OperationLogDao operationLogDao;

    @Override
    public List<OperationLog> selectAll() {
        return operationLogDao.findAll();
    }

    @Override
    public Page<OperationLog> selectPage(Integer num, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "visitTime");
        return operationLogDao.findAll(PageRequest.of(num, pageSize, sort));
    }

    @Override
    public void deleteAll() {
        operationLogDao.deleteAll();
    }

    @Override
    public void insertOne(OperationLog operationLog) {
        operationLogDao.save(operationLog);
    }

}
