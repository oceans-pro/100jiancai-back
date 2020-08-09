package oceans.listener.exception;

import javax.naming.AuthenticationException;

public class MyPasswordDisMatchException extends AuthenticationException {
    public MyPasswordDisMatchException(String explanation) {
        super(explanation);
    }
}
