package oceans.service.auth.impl;

import oceans.listener.exception.MyLockedUserException;
import oceans.listener.exception.MyUsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证处理器
 *
 * @see oceans.controller.auth.LoginController
 */
public class MySimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws ServletException, IOException {
        // -- 因为这里返回spring-security处理后的异常类...即
        // org.springframework.security.authentication.InternalAuthenticationServiceException
        // 所以要getCause
        // Throwable cause = exception.getCause();
        if (exception instanceof BadCredentialsException) {
            request.getRequestDispatcher("/login/error/0").forward(request, response);
        } else if (exception.getCause() instanceof MyUsernameNotFoundException) {
            request.getRequestDispatcher("/login/error/1").forward(request, response);
        } else if (exception.getCause() instanceof MyLockedUserException) {
            request.getRequestDispatcher("/login/error/2").forward(request, response);
        }
    }
}
