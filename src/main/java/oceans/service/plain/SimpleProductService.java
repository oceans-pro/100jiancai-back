package oceans.service.plain;

import oceans.model.ProductSimple;

import java.util.List;

public interface SimpleProductService {
    List<ProductSimple> selectAll();

    List<ProductSimple> selectSome(Integer type);

    void deleteOne(Integer id);

    void insertOne(ProductSimple product);

    void updateOne(ProductSimple product);
}
