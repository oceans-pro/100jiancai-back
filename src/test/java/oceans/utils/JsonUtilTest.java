package oceans.utils;



import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtilTest {
    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add(LocalDate.now().toString());
        String jsonStr = JsonUtil.toJsonString(list);
        System.out.println("result => " + jsonStr);
    }

    @Test
    public void test2() {
        String[] strings = JsonUtil.parseJsonString("[\"2020-07-18\",\"2020-07-18\",\"1990-07-18\"]", String[].class);
        Set set = new HashSet<>();
        for (String string: strings) {
            set.add(string);
        }
        for (Object item: set) {
            System.out.println("result => " + item);
        }
    }

    @Test
    public void test3() {
        String[] strings = JsonUtil.parseJsonString("[]", String[].class);

    }

}
