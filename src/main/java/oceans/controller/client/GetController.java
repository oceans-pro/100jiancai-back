package oceans.controller.client;

import oceans.listener.exception.MyUsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 处理官网的GET请求
 * <p>
 * 注意：
 * 1. 使用时只需要在 GetController.whiteList 中注册即可
 * 2. 这种手段可以越过spring-security的检查
 * 这么做的原因：
 * 1. 兼顾后台管理项目的Restful风格
 * 2. security的限制
 * 3. 复用controller
 * 4. 同时要区分请求是来自 官网还是管理界面从而 合理记录日志
 */
@RestController
@RequestMapping("/public/**")
public class GetController {
    public static List<String> whiteList = Arrays.asList(
            "/info/carousel",
            "/info/introduction",
            "/info/advantage",
            "/info/company_history",
            "/info/contact",
            "/hire/active",
            "/hire/config",
            "/product/type",
            "/news",
            "/visitor"
    );

    @GetMapping("/")
    public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MyUsernameNotFoundException {
        // -- test uri & url
        // System.out.println("result => " + request.getRequestURI());
        // System.out.println("result => " + request.getRequestURL());
        // news?num=1&size=3 => news
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring("/public".length()); // 必然以public开头
        for (String s: whiteList) {
            if (substring.equals(s)) {
                request.getRequestDispatcher(substring).forward(request, response);
                return;
            }
        }
        throw new MyUsernameNotFoundException("oh~no~"); // todo
    }
}
