package oceans.service.api.impl;

import lombok.extern.slf4j.Slf4j;
import oceans.service.api.MapService;
import oceans.constant.ApiConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service("gaode")
public class GaodeCityInfoServiceImpl implements MapService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("https://restapi.amap.com/v3/ip?&output=json")
    private String baseUrl; // 1.定位


    /**
     * 高德地图定位API
     * https://restapi.amap.com/v3/ip?&output=json&key=69093b2957726084b44f8e3d00016211&ip=140.75.225.124
     */
    @Override
    public String getCityInfo(String ip) {
        if (ip.startsWith("127") || ip.startsWith("192") || ip.startsWith("0.0")) {
            return "本地测试（招远）";
        }
        String url = baseUrl + "&key=" + ApiConst.GAODE_KEY + "&ip=" + ip;
        ResponseEntity<Map> entity;
        try {
            entity = restTemplate.getForEntity(url, Map.class);
        } catch (RestClientException e) {
            log.error("ip地址接口调用出现问题", e);
            return e.getMessage();
        }
        String province = entity.getBody().get("province").toString();
        String city = entity.getBody().get("city").toString();
        if ("[]".equals(province)) {
            return "[fail to get]";
        } else {
            return province + " " + city;
        }
    }
}
