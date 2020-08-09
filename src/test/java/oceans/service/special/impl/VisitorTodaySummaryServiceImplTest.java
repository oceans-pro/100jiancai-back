package oceans.service.special.impl;

import oceans.dao.VisitorTodaySummaryDao;
import oceans.model.VisitorTodaySummary;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Random;

@SpringBootTest
class VisitorTodaySummaryServiceImplTest {
    @Resource
    VisitorTodaySummaryDao visitorTodaySummaryDao;

    // 不算今天再增加9天的数据
    @Test
    void mockData() {
        LocalDate now = LocalDate.now();
        for (int i = 1; i < 20; i++) {
            LocalDate day = now.minusDays(i);
            VisitorTodaySummary summary = new VisitorTodaySummary();
            int num1 = new Random().nextInt(10) + 5;
            int num2 = new Random().nextInt(10) + 5;
            int num3 = (2 + new Random().nextInt(5)) * num1 + new Random().nextInt(10);
            int num4 = (2 + new Random().nextInt(5)) * num2 + new Random().nextInt(10);
            summary.setAdminHead(num1);
            summary.setPublicHead(num2);
            summary.setAdminClick(num3);
            summary.setPublicClick(num4);
            summary.setDate(day.toString());
            visitorTodaySummaryDao.save(summary);
        }
    }
}
