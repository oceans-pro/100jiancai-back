package oceans.dao;

import oceans.model.ProductSimple;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleProductDao extends JpaRepository<ProductSimple, Integer> {
    List<ProductSimple> findByType(Integer type, Sort sort);
}
