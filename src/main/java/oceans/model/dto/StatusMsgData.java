package oceans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ResponseEntity虽然自带状态码
 * OK(200, "OK"),
 * ACCEPTED(202, "Accepted"),
 * FORBIDDEN(403, "Forbidden"),
 * NOT_FOUND(404, "Not Found"),
 * INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
 * 但是很多都用不到，故这里使用自定义的状态码
 * <p>
 * 注意：在约定状态码的时候，适当地耦合了前端
 */
@Data
@AllArgsConstructor
public class StatusMsgData<T> {
    private final Integer status;
    private final String msg;
    private final T data;
    /**
     * 成功，不提示
     * 用于 查操作
     */
    public static Integer OK_DATA = 20000;
    /**
     * 成功，不提示
     * 用于 增删改操作
     */
    public static Integer OK_NO_TIP = 20001;
    /**
     * 成功，且提示
     * 用于 增删改操作
     */
    public static Integer OK_TIP = 20002;
    /**
     * 客户端错误，不提示
     */
    public static Integer WARN = 40000;
    /**
     * 通用的客户端错误，需要进行提示
     */
    public static Integer WARN_TIP = 40001;
    /**
     * 需要授权
     */
    public static Integer WARN_TIP_NEED_LOGIN = 40002;
    /**
     * 用户名或密码错误
     */
    public static Integer WARN_TIP_AUTH_ERROR = 40003;

    // 仅默默地查询
    public StatusMsgData(T data) {
        this.status = OK_DATA;
        this.msg = "ok";
        this.data = data;
    }

    // 增、删、改
    public StatusMsgData(Integer code, String msg) {
        this.status = code;
        this.msg = msg;
        this.data = null;
    }
}
