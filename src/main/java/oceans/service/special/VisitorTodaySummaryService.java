package oceans.service.special;

import oceans.model.VisitorTodaySummary;
import oceans.model.dto.VisitorSummaryHistoryDto;

import java.util.List;

public interface VisitorTodaySummaryService {
    void saveOrUpdateToday();

    List<VisitorTodaySummary> getLatestVisitorToday(int size);

    VisitorSummaryHistoryDto getHistory();
}
