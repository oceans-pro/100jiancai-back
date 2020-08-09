package oceans.dao;

import oceans.model.Advantage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvantageDao extends JpaRepository<Advantage, Integer> {
}
