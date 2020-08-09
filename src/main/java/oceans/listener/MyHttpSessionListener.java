package oceans.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * 统计当前的session数
 * 不能实时监测当前在线人数
 */
public class MyHttpSessionListener implements HttpSessionListener {

    public static int online = 0;

    @Override
    public synchronized void sessionCreated(HttpSessionEvent event) {
        online++;
    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent event) {
        online--;
    }
}
