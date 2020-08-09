package oceans.dao;

import oceans.model.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigDao extends JpaRepository<AppConfig, Integer> {
    AppConfig findByName(String name);
}
