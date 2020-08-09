package oceans.listener.exception;


import org.springframework.security.core.AuthenticationException;

public class MyUsernameNotFoundException extends AuthenticationException {
    public MyUsernameNotFoundException(String message) {
        super(message);
    }
}
