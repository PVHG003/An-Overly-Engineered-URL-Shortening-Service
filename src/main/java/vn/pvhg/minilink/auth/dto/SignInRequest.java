package vn.pvhg.minilink.auth.dto;

public record SignInRequest(
        String email,
        String password
) {
}
