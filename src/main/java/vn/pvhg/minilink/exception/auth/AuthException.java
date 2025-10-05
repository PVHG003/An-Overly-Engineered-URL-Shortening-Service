package vn.pvhg.minilink.exception.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.pvhg.minilink.common.ApiError;

@ControllerAdvice
public class AuthException {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAuthException(UserAlreadyExistsException ex) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(InvalidCredentailsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialsException(InvalidCredentailsException ex) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(UserNotEnabledException.class)
    public ResponseEntity<ApiError> handleUserNotEnabledException(UserNotEnabledException ex) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
