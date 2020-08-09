package oceans.dao;

import oceans.model.CompanyIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIntroductionDao extends JpaRepository<CompanyIntroduction, Integer> {
}
