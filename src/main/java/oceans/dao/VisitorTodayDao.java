package oceans.dao;

import oceans.model.VisitorToday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorTodayDao extends JpaRepository<VisitorToday, Integer> {
    int countByDatetimeStartsWithAndTypeEquals(String date, int count);

    Page<VisitorToday> findByTypeEqualsAndDatetimeStartingWith(Integer type, String date, Pageable pageable);
}
