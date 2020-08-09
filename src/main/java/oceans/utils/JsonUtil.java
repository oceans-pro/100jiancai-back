package oceans.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtil<T> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> String toJsonString(T t) {
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON转化失败");
        }
    }

    public static <T> T parseJsonString(String jsonString, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON解析失败");
        }
    }
}
