package vn.pvhg.minilink.exception.auth;

public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(String userIsNotEnabled) {
        super(userIsNotEnabled);
    }
}
