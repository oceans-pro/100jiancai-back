package oceans.listener.exception;

import org.springframework.security.core.AuthenticationException;

public class MyLockedUserException extends AuthenticationException {
    public MyLockedUserException(String msg) {
        super(msg);
    }
}
