package oceans.service.special;

import oceans.model.dto.OnlineInfo;
import org.springframework.security.core.Authentication;

public interface OnlineService {
    OnlineInfo selectAndUpdateOnlineInfo(Authentication authentication);
}
