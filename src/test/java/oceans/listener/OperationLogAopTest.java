package oceans.listener;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@SpringBootTest
@Slf4j
public class OperationLogAopTest {

    @Pointcut("execution(* oceans.listener..*(..))")
    public void logController() {
    }

    /**
     * 前置通知：获取类名、方法名
     */
    @Before("logController()")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        Class clazz = joinPoint.getTarget().getClass(); // 所访问的类
        Method method;
        String methodName = joinPoint.getSignature().getName(); // 获取访问的方法的名称
        Object[] args = joinPoint.getArgs(); // 获取访问的方法的参数
        //  -- 获取具体执行的方法的Method对象
        if (args == null || args.length == 0) { // 无参数的方法
            method = clazz.getMethod(methodName);
        } else { // 带参的方法
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            try {
                method = clazz.getMethod(methodName, classArgs);
            } catch (NoSuchMethodException | SecurityException e) {
                log.error("操作日志出错", e);
                method = null;
            }
        }
    }

}
