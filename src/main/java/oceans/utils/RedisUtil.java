package oceans.utils;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


public class RedisUtil {

    @Resource
    private static RedisTemplate redisTemplate;

    /**
     * 切换redis数据库
     *
     * @param redisTemplate springboot封装的redis对象
     * @param index         数据库下标
     */
    public static void select(RedisTemplate redisTemplate, int index) {
        LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory)redisTemplate.getConnectionFactory();
        if (lettuceConnectionFactory != null) {
            lettuceConnectionFactory.setDatabase(index);
            redisTemplate.setConnectionFactory(lettuceConnectionFactory);
            lettuceConnectionFactory.resetConnection();
        }
    }
}
