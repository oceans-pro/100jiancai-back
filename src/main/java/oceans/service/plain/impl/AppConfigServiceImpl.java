package oceans.service.plain.impl;

import oceans.dao.AppConfigDao;
import oceans.model.AppConfig;
import oceans.service.plain.AppConfigService;
import oceans.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.util.Map;

@Service
public class AppConfigServiceImpl implements AppConfigService {
    @Resource
    private AppConfigDao dao;

    @Override
    public Map getOneByName(String name) {
        AppConfig appConfig = dao.findByName(name);
        String configJson = appConfig.getConfig();
        return JsonUtil.parseJsonString(configJson, Map.class);
    }

    @Override
    public void updateOne(String name, Map configMap) {
        AppConfig appConfig = dao.findByName(name);
        String json = JsonUtil.toJsonString(configMap);
        appConfig.setConfig(json);
        dao.save(appConfig);
    }
}
