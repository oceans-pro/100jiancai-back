package oceans.controller.auth;


import oceans.model.dto.StatusMsgData;
import oceans.service.auth.impl.MySimpleUrlAuthenticationFailureHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "/need_login", method = {RequestMethod.GET, RequestMethod.PUT})
    public ResponseEntity<StatusMsgData<String>> loginPage() {
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.WARN_TIP_NEED_LOGIN, "需要先授权", ""),
                HttpStatus.UNAUTHORIZED
        );
    }

    @GetMapping("/success")
    public ResponseEntity<StatusMsgData<String>> loginSuccess() {
        return new ResponseEntity<>(
                new StatusMsgData<>(StatusMsgData.OK_DATA, "已授权", ""),
                HttpStatus.OK
        );
    }

    // -- 配合使用.failureUrl("/login/error")
    // 缺点：无法具体判断用户是否被封禁
    // @GetMapping("/error")
    // public ResponseEntity<StatusMsgData<String>> loginError() {
    //     System.out.println("result => " + 123);
    //     return new ResponseEntity<>(
    //             new StatusMsgData<>(StatusMsgData.WARN_TIP_AUTH_ERROR, "登录失败", ""),
    //             HttpStatus.OK
    //     );
    // }

    /**
     * -- 用户登录失败后跳转的路由
     *
     * @see MySimpleUrlAuthenticationFailureHandler
     */
    @PostMapping("/error/0")
    public ResponseEntity<StatusMsgData<String>> loginError0() {
        return ResponseEntity.ok(
                new StatusMsgData<>(StatusMsgData.WARN_TIP, "密码错误！")
        );
    }

    @PostMapping("/error/1")
    public ResponseEntity<StatusMsgData<String>> loginError1() {
        return ResponseEntity.ok(
                new StatusMsgData<>(StatusMsgData.WARN_TIP, "用户不存在！")
        );
    }

    @PostMapping("/error/2")
    public ResponseEntity<StatusMsgData<String>> loginError2() {
        return ResponseEntity.ok(
                new StatusMsgData<>(StatusMsgData.WARN_TIP, "您的账号被封禁，暂时无法登录！")
        );
    }
}
