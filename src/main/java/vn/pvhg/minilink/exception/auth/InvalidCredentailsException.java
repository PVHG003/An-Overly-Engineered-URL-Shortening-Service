package vn.pvhg.minilink.exception.auth;

public class InvalidCredentailsException extends RuntimeException {
    public InvalidCredentailsException(String invalidEmailOrPassword) {
        super(invalidEmailOrPassword);
    }
}
