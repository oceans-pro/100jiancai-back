package oceans.controller.special;

import oceans.model.VisitorIp;
import oceans.model.VisitorToday;
import oceans.model.VisitorTodaySummary;
import oceans.model.dto.StatusMsgData;
import oceans.model.dto.VisitorSummaryHistoryDto;
import oceans.service.special.VisitorIpService;
import oceans.service.special.VisitorTodayService;
import oceans.service.special.VisitorTodaySummaryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/visitor")
public class VisitorController {
    @Resource
    private VisitorTodayService visitorTodayService;
    @Resource
    private VisitorTodaySummaryService visitorTodaySummaryService;
    @Resource
    private VisitorIpService visitorIpService;

    /**
     * 获取今日访客
     *
     * @param date 2000-12-12
     */
    @GetMapping("/today/{date}")
    @PreAuthorize("hasAuthority('visitor:get')")
    public ResponseEntity<StatusMsgData<Page<VisitorToday>>> getTodayVisitorList(
            @PathVariable("date") String date, Integer type, Integer num, Integer size) {
        Page<VisitorToday> visitorToday = visitorTodayService.selectVisitorTodayList(type, date, num, size);
        return ResponseEntity.ok(new StatusMsgData<>(visitorToday));
    }

    /**
     * 获取历史访客的IP
     */
    @GetMapping("/ip")
    @PreAuthorize("hasAuthority('visitor:get')")
    public ResponseEntity<StatusMsgData<Page<VisitorIp>>> getHistoryIp(Integer type, Integer num, Integer size) {
        Page<VisitorIp> visitorIpPage = visitorIpService.getVisitorIpPage(type, num, size);
        return ResponseEntity.ok(new StatusMsgData<>(visitorIpPage));
    }

    /**
     * 记录访客
     *
     * @param type    来自admin还是public
     * @param refer   来源，是百度还是搜网址
     * @param request 携带ip等信息的请求
     */
    @GetMapping
    public ResponseEntity<StatusMsgData<String>> logVisitor(
            Integer type, Integer device, String refer, HttpServletRequest request) {
        if ((type != 0 && type != 1) || (device != 0 && device != 1)) {
            return ResponseEntity.ok(new StatusMsgData<>("别闹"));
        }
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String ip;
        if (xForwardedFor == null) { // 没有使用Nginx
            ip = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)) { // 美化本地ip
                ip = "127.0.0.1";
            }
        } else { // 使用了Nginx
            ip = request.getHeader("X-Forwarded-For").split(",")[0];
        }
        visitorTodayService.insertOne(ip, device, type, refer);
        return ResponseEntity.ok(new StatusMsgData<>("hello"));
    }

    /**
     * 获取最近访客的趋势
     */
    @GetMapping("/latest/{size}")
    public ResponseEntity<StatusMsgData<List<VisitorTodaySummary>>> getLatestVisitorToday(
            @PathVariable("size") int size) {
        if (size > 20) {
            return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "太大了"));
        }
        return ResponseEntity.ok(new StatusMsgData<>(visitorTodaySummaryService.getLatestVisitorToday(size)));
    }

    /**
     * 获取历史访问人数/人次
     */
    @GetMapping("/history")
    public ResponseEntity<StatusMsgData<VisitorSummaryHistoryDto>> getLatestVisitorToday() {
        return ResponseEntity.ok(new StatusMsgData<>(visitorTodaySummaryService.getHistory()));
    }
}
