package oceans.dao.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class VisitorTodayMapperTest {
    @Autowired
    VisitorTodayMapper visitorTodayMapper;

    @Test
    void test1() {
        int i = visitorTodayMapper.selectCountByDateAndByTypeAndWithIpDistinct(LocalDate.now().toString(), 1);
        System.out.println("result => " + i);
    }

    @Test
    void test2(){
        List<String> list = visitorTodayMapper.selectDistinctIPList(LocalDate.now().toString(), 0);
        System.out.println(list);
    }
}
