package oceans.service.special;

import oceans.model.VisitorToday;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VisitorTodayService {

    void insertOne(String ip,Integer device, Integer type, String refer);

    int selectCountByDateAndType(String date, int type);

    int selectCountByDateAndTypeWithDistinctIp(String date, int type);

    Page<VisitorToday> selectVisitorTodayList(Integer type, String date, Integer num, Integer size);
}
