package vn.pvhg.minilink.exception.auth;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String emailAlreadyRegistered) {
        super(emailAlreadyRegistered);
    }
}
