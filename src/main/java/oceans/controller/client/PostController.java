package oceans.controller.client;

import oceans.listener.exception.MyUsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 处理官网的post请求
 */
@RestController
@RequestMapping("/public/**")
public class PostController {
    public static List<String> whiteList = Arrays.asList(
            "/message"
    );

    @PostMapping("/")
    public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MyUsernameNotFoundException {
        String substring = request.getRequestURI().substring("/public".length());
        for (String s: whiteList) {
            if (substring.equals(s)) {
                request.getRequestDispatcher(substring).forward(request, response);
                return;
            }
        }
        throw new MyUsernameNotFoundException("Big brother is watching you!"); // todo
    }
}
