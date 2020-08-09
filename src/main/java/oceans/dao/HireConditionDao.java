package oceans.dao;

import oceans.model.HireCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HireConditionDao extends JpaRepository<HireCondition, Integer> {
    List<HireCondition> findByActiveTrue();
}
