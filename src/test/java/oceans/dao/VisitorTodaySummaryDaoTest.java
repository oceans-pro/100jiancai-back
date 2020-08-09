package oceans.dao;

import net.bytebuddy.asm.Advice;
import oceans.model.VisitorTodaySummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VisitorTodaySummaryDaoTest {
    @Autowired
    private VisitorTodaySummaryDao dao;
    @Test
    void test11() {
        VisitorTodaySummary byDateEquals = dao.findByDateEquals(LocalDate.now().toString()+"1231");
        System.out.println("result => " + byDateEquals);
    }
}
