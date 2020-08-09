package oceans.service.plain.impl;

import oceans.dao.HireConditionDao;
import oceans.model.HireCondition;
import oceans.service.plain.HireConditionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HireConditionServiceImpl implements HireConditionService {
    @Resource
    private HireConditionDao hireConditionDao;

    @Override
    public List<HireCondition> selectAll() {
        return hireConditionDao.findAll();
    }

    @Override
    public List<HireCondition> selectSome() {
        return hireConditionDao.findByActiveTrue();
    }


    @Override
    public void deleteOne(Integer id) {
        hireConditionDao.deleteById(id);
    }

    @Override
    public void updateOne(HireCondition hireCondition) {
        hireConditionDao.save(hireCondition);
    }

    @Override
    public void insertOne(HireCondition hireCondition) {
        hireConditionDao.save(hireCondition);
    }
}
