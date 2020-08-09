package oceans.dao;

import oceans.model.VisitorIp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorIpDao extends JpaRepository<VisitorIp, Integer> {
    VisitorIp findByTypeAndIp(Integer type, String ip);

    Page<VisitorIp> findByType(int type, Pageable pageable);
}
