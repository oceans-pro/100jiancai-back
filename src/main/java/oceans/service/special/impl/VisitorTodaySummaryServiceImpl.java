package oceans.service.special.impl;

import oceans.dao.VisitorTodaySummaryDao;
import oceans.dao.mapper.VisitorTodaySummaryMapper;
import oceans.model.VisitorTodaySummary;
import oceans.model.dto.VisitorSummaryHistoryDto;
import oceans.service.special.VisitorTodayService;
import oceans.service.special.VisitorTodaySummaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class VisitorTodaySummaryServiceImpl implements VisitorTodaySummaryService {
    @Resource
    VisitorTodaySummaryDao visitorTodaySummaryDao;
    @Resource
    VisitorTodaySummaryMapper visitorTodaySummaryMapper;
    @Resource
    VisitorTodayService visitorTodayService;

    @Override
    @Transactional
    public void saveOrUpdateToday() {
        VisitorTodaySummary summary;
        String date = LocalDate.now().toString();
        VisitorTodaySummary existOne = visitorTodaySummaryDao.findByDateEquals(date);
        if (existOne != null) {
            summary = existOne;
        } else {
            summary = new VisitorTodaySummary();
        }
        int adminClick = visitorTodayService.selectCountByDateAndType(date, 0);
        int adminHead = visitorTodayService.selectCountByDateAndTypeWithDistinctIp(date, 0);
        int publicClick = visitorTodayService.selectCountByDateAndType(date, 1);
        int publicHead = visitorTodayService.selectCountByDateAndTypeWithDistinctIp(date, 1);
        summary.setAdminClick(adminClick);
        summary.setAdminHead(adminHead);
        summary.setPublicClick(publicClick);
        summary.setPublicHead(publicHead);
        summary.setDate(date);
        visitorTodaySummaryDao.save(summary);
    }

    @Override
    public List<VisitorTodaySummary> getLatestVisitorToday(int size) {
        Page<VisitorTodaySummary> page = visitorTodaySummaryDao.findAll(
                PageRequest.of(0,
                        size,
                        Sort.by(Sort.Direction.DESC, "date")
                )
        );
        return page.getContent();
    }

    @Override
    public VisitorSummaryHistoryDto getHistory() {
        return visitorTodaySummaryMapper.selectVisitorSumHistory();
    }
}
