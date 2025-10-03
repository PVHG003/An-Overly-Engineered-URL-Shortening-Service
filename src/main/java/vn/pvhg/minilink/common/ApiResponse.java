package vn.pvhg.minilink.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private HttpStatus status;
    private String message;
    private T data;
}
