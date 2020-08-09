package oceans.service.special;

import oceans.model.VisitorIp;
import org.springframework.data.domain.Page;

public interface VisitorIpService {
    void saveTodayIp(String today);

    Page<VisitorIp> getVisitorIpPage(int type, int num, int size);
}
