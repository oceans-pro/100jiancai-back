package oceans.service.plain;

import oceans.model.Advantage;

import java.util.List;

public interface AdvantageService {
    List<Advantage> findAllAdvantages();

    void updateAll(List<Advantage> advantages);

    void updateOne(Integer id, Advantage advantage);
}
