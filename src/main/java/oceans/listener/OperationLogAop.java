package oceans.listener;

import lombok.extern.slf4j.Slf4j;
import oceans.model.OperationLog;
import oceans.service.special.impl.OperationServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class OperationLogAop {
    @Resource
    private HttpServletRequest request;
    @Resource
    private OperationServiceImpl service;

    private Date visitTime; // 开始时间
    private Class clazz; // 访问的类
    private Method method; // 访问的方法
    private String uri; // 访问的uri
    private Object[] args; // 访问的参数
    private Parameter[] parameters; // 访问的参数名字 method.getParameters()

    @Pointcut("execution(* oceans.controller.info.*.*(..)) || " +
            "execution(* oceans.controller.plain.*.*(..)) || " +
            "execution(* oceans.controller.auth.PermissionController.*(..)) || " +
            "execution(* oceans.controller.auth.RoleController.*(..)) || " +
            "execution(* oceans.controller.auth.UserController.*(..))"
    )
    public void logController() {
    }

    /**
     * 前置通知：获取类名、方法名
     */
    @Before("logController()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = ""; // 获取访问的方法的名称
        try {
            visitTime = new Date();
            clazz = joinPoint.getTarget().getClass(); // 所访问的类
            methodName = joinPoint.getSignature().getName();
            args = joinPoint.getArgs(); // 获取访问的方法的参数
            //  -- 获取具体执行的方法的Method对象
            if (args == null || args.length == 0) { // 无参数的方法
                method = clazz.getMethod(methodName);
            } else { // 带参的方法
                Class[] classArgs = new Class[args.length];
                try {
                    for (int i = 0; i < args.length; i++) {
                        classArgs[i] = args[i].getClass();
                    }
                } catch (NullPointerException e) {
                    log.error("传入的参数有一个为null", e);
                }
                method = clazz.getMethod(methodName, classArgs);
            }
        } catch (NoSuchMethodException e) {
            method = null;
        }
    }

    /**
     * 后置通知：获取耗时
     */
    @After("logController()")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        if (clazz != null && method != null && clazz != OperationLogAop.class) {
            // -- 获取类上的@RequestMapping("/xxx")
            RequestMapping classAnnotation = (RequestMapping)clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classUriValue = classAnnotation.value();
                // -- 获取方法上的@XXXMapping("/xxx")
                PutMapping annotation1 = method.getAnnotation(PutMapping.class);
                if (annotation1 != null) {
                    String[] methodUriValue = annotation1.value();
                    if (methodUriValue == null || methodUriValue.length == 0) { // @PotMapping
                        uri = "PUT: " + classUriValue[0];
                    } else { // @PotMapping("/xxx")
                        uri = "PUT: " + classUriValue[0] + methodUriValue[0];
                    }
                    service.insertOne(this.combine());
                }
                PostMapping annotation2 = method.getAnnotation(PostMapping.class);
                if (annotation2 != null) {
                    String[] methodUriValue = annotation2.value();
                    if (methodUriValue == null || methodUriValue.length == 0) {
                        uri = "POST: " + classUriValue[0];
                    } else {
                        uri = "POST: " + classUriValue[0] + methodUriValue[0];
                    }
                    service.insertOne(this.combine());
                }
                DeleteMapping annotation3 = method.getAnnotation(DeleteMapping.class);
                if (annotation3 != null) {
                    String[] methodUriValue = annotation3.value();
                    if (methodUriValue == null || methodUriValue.length == 0) {
                        uri = "DELETE: " + classUriValue[0];
                    } else {
                        uri = "DELETE: " + classUriValue[0] + methodUriValue[0];
                    }
                    service.insertOne(this.combine());
                }
            }
        }
    }

    /**
     * 将日志相关信息封装到AppLog对象
     */
    private OperationLog combine() {
        // 获取真实IP
        String ip;
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor == null) { // 没有使用Nginx
            ip = request.getRemoteAddr();
        } else { // 使用了Nginx
            ip = request.getHeader("X-Forwarded-For").split(",")[0];
        }

        long time = new Date().getTime() - visitTime.getTime(); // 获取耗费时间
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails)context.getAuthentication().getPrincipal();
        String username = user.getUsername(); // 获取当前操作的用户

        Parameter[] parameters = method.getParameters();
        String parametersStr = "";
        String argsStr = "";
        for (Parameter p: parameters) {
            parametersStr = parametersStr + p + ",";
        }
        if (!StringUtils.isEmpty(parametersStr)) {
            parametersStr = parametersStr.substring(0, parametersStr.length() - 1);
        }
        for (Object object: args) {
            argsStr = argsStr + object;
        }
        OperationLog operationLog = new OperationLog();
        operationLog.setUri(uri);
        operationLog.setUsername(username);
        operationLog.setVisitTime(visitTime);
        operationLog.setExecutionTime(time);
        operationLog.setIp(ip);
        operationLog.setMethod(clazz.getName() +
                "." +
                (method != null ? method.getName() : "无") +
                "(" +
                parametersStr +
                ")"
        );
        operationLog.setData(argsStr);
        return operationLog;
    }
}

