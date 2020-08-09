package oceans.service.plain;

import oceans.model.CompanyHistory;

import java.util.List;

public interface CompanyHistoryService {
    List<CompanyHistory> selectAll();

    void inactiveOne(Integer id);

    void insertOne(CompanyHistory companyHistory);

    void updateOne(CompanyHistory companyHistory);
}
