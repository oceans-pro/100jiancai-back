package oceans.service.plain;

import oceans.model.AppConfig;

import java.util.Map;

public interface AppConfigService {

    /**
     * 根据配置名获取配置Map
     */
    Map getOneByName(String name);

    void updateOne(String hire, Map configMap);
}
