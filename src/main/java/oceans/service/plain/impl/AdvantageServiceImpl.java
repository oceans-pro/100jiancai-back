package oceans.service.plain.impl;

import oceans.dao.AdvantageDao;
import oceans.model.Advantage;
import oceans.service.plain.AdvantageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdvantageServiceImpl implements AdvantageService {
    @Resource
    private AdvantageDao advantageDao;

    @Override
    public List<Advantage> findAllAdvantages() {
        return advantageDao.findAll();
    }

    @Override
    public void updateAll(List<Advantage> advantages) {
        advantageDao.saveAll(advantages);
    }

    @Override
    public void updateOne(Integer id, Advantage advantage) {
        advantageDao.save(advantage);
    }
}
