package oceans.dao.mapper;

import oceans.model.dto.VisitorSummaryHistoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VisitorTodaySummaryMapperTest {
    @Autowired
    VisitorTodaySummaryMapper mapper;

    @Test
    public void test1() {
        VisitorSummaryHistoryDto visitorSummaryHistoryDto = mapper.selectVisitorSumHistory();
        System.out.println("result => " + visitorSummaryHistoryDto);
    }
}
