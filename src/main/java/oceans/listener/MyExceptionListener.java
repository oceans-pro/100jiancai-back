package oceans.listener;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javassist.NotFoundException;
import oceans.model.dto.StatusMsgData;
import oceans.listener.exception.MyUsernameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 统一错误处理
 */
@RestControllerAdvice
public class MyExceptionListener {

    @ExceptionHandler(MyUsernameNotFoundException.class)
    public ResponseEntity<StatusMsgData<String>> handleMySecurityException(Exception e) {
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, e.getMessage()));
    }

    @ExceptionHandler(MysqlDataTruncation.class)
    public ResponseEntity<StatusMsgData<String>> handleMysqlDataTruncation() {
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "输入过大！"));
    }

    @ExceptionHandler(NotFoundException.class) // 貌似拦截不到
    public ResponseEntity<StatusMsgData<String>> handle404() {
        return ResponseEntity.ok(new StatusMsgData<>(StatusMsgData.WARN_TIP, "404！"));
    }
}
