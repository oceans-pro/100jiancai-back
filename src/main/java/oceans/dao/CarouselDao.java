package oceans.dao;

import oceans.model.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselDao extends JpaRepository<Carousel, Integer> {
    int deleteByName(String name);
}
