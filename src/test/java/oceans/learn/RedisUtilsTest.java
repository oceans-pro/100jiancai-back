package oceans.learn;

import oceans.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisUtilsTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        RedisUtil.select(redisTemplate, 11);
        redisTemplate.opsForValue().set(111, 1111);
    }
}
