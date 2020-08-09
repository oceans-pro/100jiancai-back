package oceans.service.task;

import oceans.service.special.VisitorIpService;
import oceans.service.special.VisitorTodaySummaryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class DailyTask {
    @Resource
    VisitorTodaySummaryService visitorTodaySummaryService;
    @Resource
    VisitorIpService visitorIpService;

    /**
     * 将 `表_访客记录` 定时汇总到 `汇总表_访客数目`
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void visitorToday() {
        visitorTodaySummaryService.saveOrUpdateToday();
    }

    /**
     * 记录访客IP，每天一次
     */
    @Scheduled(cron = "0 30 2 * * ?")
    public void logIp() {
        visitorIpService.saveTodayIp(LocalDate.now().toString());
    }
}
