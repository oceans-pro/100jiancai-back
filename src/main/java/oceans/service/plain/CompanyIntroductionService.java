package oceans.service.plain;

import oceans.model.CompanyIntroduction;

public interface CompanyIntroductionService {
    CompanyIntroduction selectTheCompanyIntroduction();

    void updateTheCompanyIntroduction(CompanyIntroduction companyIntroduction);
}
