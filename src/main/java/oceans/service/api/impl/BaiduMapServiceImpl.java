package oceans.service.api.impl;

import lombok.extern.slf4j.Slf4j;
import oceans.service.api.MapService;
import oceans.constant.ApiConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service("baidu")
public class BaiduMapServiceImpl implements MapService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("https://api.map.baidu.com/location/ip?coor=bd09ll")
    private String baseUrl;

    /**
     * 比高德准确！！！不会把我家定位到青岛
     * https://api.map.baidu.com/location/ip?ak=6cjPPTrS9TiK2CTgRAu1jtiTsjvo6rEG&ip=140.75.225.124&coor=bd09ll
     */
    @Override
    public String getCityInfo(String ip) {
        if (ip.startsWith("127") || ip.startsWith("192") || ip.startsWith("0:0")) {
            return "本地测试（招远）";
        }
        String url = baseUrl + "&ak=" + ApiConst.BAIDU_AK + "&ip=" + ip;
        ResponseEntity<Map> entity = restTemplate.getForEntity(url, Map.class);
        Map map = entity.getBody();
        Map<String, Object> content = (Map<String, Object>)map.get("content");
        String string = content.get("address").toString();
        return string;
    }
}
