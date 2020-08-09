package oceans.service.plain.impl;

import oceans.dao.CompanyIntroductionDao;
import oceans.model.CompanyIntroduction;
import oceans.service.plain.CompanyIntroductionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompanyIntroductionServiceImpl implements CompanyIntroductionService {
    @Resource
    private CompanyIntroductionDao companyIntroductionDao;

    @Override
    public CompanyIntroduction selectTheCompanyIntroduction() {
        CompanyIntroduction companyIntroduction = new CompanyIntroduction();
        companyIntroduction.setHtml("<p>check mysql!</p>");
        return companyIntroductionDao.findById(1).orElse(companyIntroduction);
    }

    @Override
    public void updateTheCompanyIntroduction(CompanyIntroduction companyIntroduction) {
        companyIntroduction.setId(1);
        companyIntroductionDao.save(companyIntroduction);
    }
}
