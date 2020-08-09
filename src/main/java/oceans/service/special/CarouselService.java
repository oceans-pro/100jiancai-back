package oceans.service.special;

import oceans.model.Carousel;

import java.util.List;

public interface CarouselService {
    /**
     * 向数据库以及存储云上插入一条轮播
     */
    void insertOne(Carousel carousel);

    /**
     * 向数据库以及存储云上插入一条轮播
     */
    int deleteOne(String name);

    List<Carousel> selectAll();
}
