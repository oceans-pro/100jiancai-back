package oceans.service.plain;

import oceans.model.HireCondition;

import java.util.List;

public interface HireConditionService {

    List<HireCondition> selectAll();
    List<HireCondition> selectSome();

    void deleteOne(Integer id);

    void insertOne(HireCondition hireCondition);

    void updateOne(HireCondition hireCondition);

}
