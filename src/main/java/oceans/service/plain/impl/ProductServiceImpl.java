package oceans.service.plain.impl;

import oceans.dao.SimpleProductDao;
import oceans.model.ProductSimple;
import oceans.service.plain.SimpleProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements SimpleProductService {
    @Resource
    private SimpleProductDao simpleProductDao;

    @Override
    public List<ProductSimple> selectAll() {
        return simpleProductDao.findAll();
    }

    @Override
    public List<ProductSimple> selectSome(Integer type) {
        return simpleProductDao.findByType(type, Sort.by(Sort.Direction.ASC, "position"));
    }

    @Override
    public void deleteOne(Integer id) {
        simpleProductDao.deleteById(id);
    }

    @Override
    public void insertOne(ProductSimple product) {
        simpleProductDao.save(product);
    }

    @Override
    public void updateOne(ProductSimple product) {
        simpleProductDao.save(product);
    }
}
