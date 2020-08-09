package oceans.dao;

import oceans.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsDao extends JpaRepository<News, Integer>, JpaSpecificationExecutor<News> {
}
