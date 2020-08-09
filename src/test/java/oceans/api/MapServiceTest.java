package oceans.api;

import oceans.service.api.MapService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
public class MapServiceTest {
    @Resource(name = "baidu")
    private MapService mapService1;
    @Resource(name = "gaode")
    private MapService mapService2;

    @Test
    public void test1() {
        String cityInfo1 = mapService1.getCityInfo("140.75.225.124");
        String cityInfo2 = mapService2.getCityInfo("140.75.225.124");
        String cityInfo3 = mapService2.getCityInfo("27.192.172.49");
        String cityInfo4 = mapService2.getCityInfo("27.192.172.49");
    }
}
