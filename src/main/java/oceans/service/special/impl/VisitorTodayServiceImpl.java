package oceans.service.special.impl;

import oceans.dao.VisitorTodayDao;
import oceans.dao.mapper.VisitorTodayMapper;
import oceans.model.VisitorToday;
import oceans.service.special.VisitorTodayService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorTodayServiceImpl implements VisitorTodayService {
    @Resource
    private VisitorTodayDao visitorTodayDao;
    @Resource
    private VisitorTodayMapper visitorTodayMapper;

    @Override
    public void insertOne(String ip,Integer device, Integer type, String refer) {
        VisitorToday visitorToday = new VisitorToday();
        visitorToday.setId(null);
        visitorToday.setDatetime(LocalDateTime.now().toString());
        visitorToday.setIp(ip);
        visitorToday.setType(type);
        visitorToday.setDevice(device);
        visitorToday.setRefer(refer);
        visitorTodayDao.save(visitorToday);
    }

    @Override
    public int selectCountByDateAndType(String date, int count) {
        return visitorTodayDao.countByDatetimeStartsWithAndTypeEquals(date, count);
    }

    @Override
    public int selectCountByDateAndTypeWithDistinctIp(String date, int count) {
        return visitorTodayMapper.selectCountByDateAndByTypeAndWithIpDistinct(date, count);
    }

    @Override
    public Page<VisitorToday> selectVisitorTodayList(Integer type, String date, Integer num, Integer size) {
        return visitorTodayDao.findByTypeEqualsAndDatetimeStartingWith(
                type,
                date,
                PageRequest.of(num, size, Sort.by(Sort.Direction.DESC, "datetime"))
        );
    }
}
