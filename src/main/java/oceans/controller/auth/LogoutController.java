package oceans.controller.auth;


import oceans.model.dto.StatusMsgData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logout")
public class LogoutController {

    // not public
    @GetMapping("/success")
    public ResponseEntity<StatusMsgData<String>> logoutSuccess() {
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_NO_TIP, "注销成功", ""),
                HttpStatus.OK
        );
    }
}
