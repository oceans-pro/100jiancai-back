package oceans.dao;

import oceans.model.CompanyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyHistoryDao extends JpaRepository<CompanyHistory, Integer> {
    List<CompanyHistory> findByActiveTrueOrderByDate();
}
