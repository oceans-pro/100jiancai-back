package oceans.dao;

import oceans.model.VisitorToday;
import oceans.model.VisitorTodaySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorTodaySummaryDao extends JpaRepository<VisitorTodaySummary, Integer> {
    VisitorTodaySummary findByDateEquals(String string);
}
