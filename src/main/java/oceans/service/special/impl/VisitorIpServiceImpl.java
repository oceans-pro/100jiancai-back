package oceans.service.special.impl;

import oceans.dao.VisitorIpDao;
import oceans.dao.mapper.VisitorTodayMapper;
import oceans.model.VisitorIp;
import oceans.service.special.VisitorIpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VisitorIpServiceImpl implements VisitorIpService {
    @Resource
    VisitorTodayMapper visitorTodayMapper;
    @Resource
    VisitorIpDao visitorIpDao;

    @Override
    public void saveTodayIp(String today) { // 日期不要直接写在业务类中，而是写在Task里面
        for (int type = 0; type < 2; type++) {
            List<String> ipList = visitorTodayMapper.selectDistinctIPList(today, type);
            for (String ip: ipList) {
                VisitorIp visitorIp;
                VisitorIp existOne = visitorIpDao.findByTypeAndIp(type, ip);
                if (existOne == null) {
                    visitorIp = new VisitorIp();
                    visitorIp.setIp(ip);
                } else {
                    visitorIp = existOne;
                }
                visitorIp.setLastDate(today);
                visitorIp.setType(type);
                visitorIpDao.save(visitorIp);
            }
        }
    }

    @Override
    public Page<VisitorIp> getVisitorIpPage(int type, int num, int size) {
        return visitorIpDao.findByType(
                type,
                PageRequest.of(num, size, Sort.by(Sort.Direction.DESC, "lastDate"))
        );
    }
}
