package oceans.service.special.impl;

import oceans.constant.ApiConst;
import oceans.dao.CarouselDao;
import oceans.model.Carousel;
import oceans.service.api.OssService;
import oceans.service.special.CarouselService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Resource
    private CarouselDao carouselDao;
    @Resource
    private OssService ossService;

    @Override
    public void insertOne(Carousel carousel) {
        carousel.setId(null);
        carouselDao.save(carousel);
    }

    @Override
    public int deleteOne(String name) {
        ossService.deleteFileByKey(ApiConst.QINIU_BUCKET_PUBLIC, name);
        return carouselDao.deleteByName(name);
    }

    @Override
    public List<Carousel> selectAll() {
        return carouselDao.findAll();
    }
}
