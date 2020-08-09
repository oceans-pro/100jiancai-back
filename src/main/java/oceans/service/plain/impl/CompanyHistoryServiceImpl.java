package oceans.service.plain.impl;

import oceans.dao.CompanyHistoryDao;
import oceans.model.CompanyHistory;
import oceans.service.plain.CompanyHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyHistoryServiceImpl implements CompanyHistoryService {
    @Resource
    private  CompanyHistoryDao companyHistoryDao;

    @Override
    public List<CompanyHistory> selectAll() {
        return companyHistoryDao.findByActiveTrueOrderByDate();
    }

    @Override
    public void inactiveOne(Integer id) {
        CompanyHistory companyHistory = companyHistoryDao.findById(id).orElse(null);
        if (companyHistory != null) {
            companyHistory.setActive(false);
            companyHistoryDao.save(companyHistory);
        }
    }

    @Override
    public void insertOne(CompanyHistory companyHistory) {
        companyHistoryDao.save(companyHistory);
    }

    @Override
    public void updateOne(CompanyHistory companyHistory) {
        companyHistoryDao.save(companyHistory);
    }
}
